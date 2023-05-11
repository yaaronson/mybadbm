package edu.touro.mco152.bm.commands;

import edu.touro.mco152.bm.App;
import edu.touro.mco152.bm.DiskMark;
import edu.touro.mco152.bm.Util;
import edu.touro.mco152.bm.commands.CmdBenchmark;
import edu.touro.mco152.bm.persist.DiskRun;
import edu.touro.mco152.bm.persist.EM;
import edu.touro.mco152.bm.programUI;
import edu.touro.mco152.bm.ui.Gui;
import jakarta.persistence.EntityManager;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static edu.touro.mco152.bm.App.*;
import static edu.touro.mco152.bm.App.msg;
import static edu.touro.mco152.bm.DiskMark.MarkType.WRITE;

/**
 * This class implements the CmdBenchmark interface and defines the
 * execute method for running a write benchmark on the disk.
 */

public class WriteBenchmark implements CmdBenchmark {

    programUI pu;
    int numOfBlocks;
    int numOfMarks;
    int blockSizeKb;
    DiskRun.BlockSequence blockSequence;

    public WriteBenchmark(programUI pu, int numOfBlocks, int numOfMarks, int blockSizeKb,
                           DiskRun.BlockSequence blockSequence) {
        this.pu = pu;
        this.numOfBlocks = numOfBlocks;
        this.numOfMarks = numOfMarks;
        this.blockSizeKb = blockSizeKb;
        this.blockSequence = blockSequence;
    }


    /**
     *  This method Executes the write benchmark.
     *
     *  This method initializes the variables, writes data to files,
     *  calculates benchmarks, updates progress, and saves the benchmark information.
     *  It also updates the user interface to reflect the progress and adds the benchmark run to the GUI panel.
     */
    @Override
    public void execute() {

        /*
          init local vars that keep track of benchmarks, and a large read/write buffer
         */
        int wUnitsComplete = 0, rUnitsComplete = 0, unitsComplete;
        int wUnitsTotal = App.writeTest ? numOfBlocks * numOfMarks : 0;
        int rUnitsTotal = App.readTest ? numOfBlocks * numOfMarks : 0;
        int unitsTotal = wUnitsTotal + rUnitsTotal;
        float percentComplete;

        int blockSize = blockSizeKb * KILOBYTE;
        byte[] blockArr = new byte[blockSize];
        for (int b = 0; b < blockArr.length; b++) {
            if (b % 2 == 0) {
                blockArr[b] = (byte) 0xFF;
            }
        }

        DiskMark wMark;  // declare vars that will point to object used to pass progress to UI

        Gui.updateLegend();  // init chart legend info

        if (App.autoReset) {
            App.resetTestData();
            Gui.resetTestData();
        }

        int startFileNum = App.nextMarkNumber;

            DiskRun run = new DiskRun(DiskRun.IOMode.WRITE, blockSequence);
            run.setNumMarks(numOfMarks);
            run.setNumBlocks(numOfBlocks);
            run.setBlockSize(blockSizeKb);
            run.setTxSize(App.targetTxSizeKb());
            run.setDiskInfo(Util.getDiskInfo(dataDir));

            // Tell logger and GUI to display what we know so far about the Run
            msg("disk info: (" + run.getDiskInfo() + ")");

            Gui.chartPanel.getChart().getTitle().setVisible(true);
            Gui.chartPanel.getChart().getTitle().setText(run.getDiskInfo());

            // Create a test data file using the default file system and config-specified location
            if (!App.multiFile) {
                testFile = new File(dataDir.getAbsolutePath() + File.separator + "testdata.jdm");
            }

            /*
              Begin an outer loop for specified duration (number of 'marks') of benchmark,
              that keeps writing data (in its own loop - for specified # of blocks). Each 'Mark' is timed
              and is reported to the GUI for display as each Mark completes.
             */
            for (int m = startFileNum; m < startFileNum + numOfMarks && !pu.isCancelledpi(); m++) {

                if (App.multiFile) {
                    testFile = new File(dataDir.getAbsolutePath()
                            + File.separator + "testdata" + m + ".jdm");
                }
                wMark = new DiskMark(WRITE);    // starting to keep track of a new benchmark
                wMark.setMarkNum(m);
                long startTime = System.nanoTime();
                long totalBytesWrittenInMark = 0;

                String mode = "rw";
                if (App.writeSyncEnable) {
                    mode = "rwd";
                }

                try {
                    try (RandomAccessFile rAccFile = new RandomAccessFile(testFile, mode)) {
                        for (int b = 0; b < numOfBlocks; b++) {
                            if (blockSequence == DiskRun.BlockSequence.RANDOM) {
                                int rLoc = Util.randInt(0, numOfBlocks - 1);
                                rAccFile.seek((long) rLoc * blockSize);
                            } else {
                                rAccFile.seek((long) b * blockSize);
                            }
                            rAccFile.write(blockArr, 0, blockSize);
                            totalBytesWrittenInMark += blockSize;
                            wUnitsComplete++;
                            unitsComplete = rUnitsComplete + wUnitsComplete;
                            percentComplete = (float) unitsComplete / (float) unitsTotal * 100f;

                            /*
                              Report to GUI what percentage level of Entire BM (#Marks * #Blocks) is done.
                             */
                            pu.setProgresspi((int) percentComplete);
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                }

                /*
                  Compute duration, throughput of this Mark's step of BM
                 */
                long endTime = System.nanoTime();
                long elapsedTimeNs = endTime - startTime;
                double sec = (double) elapsedTimeNs / (double) 1000000000;
                double mbWritten = (double) totalBytesWrittenInMark / (double) MEGABYTE;
                wMark.setBwMbSec(mbWritten / sec);
                msg("m:" + m + " write IO is " + wMark.getBwMbSecAsString() + " MB/s     "
                        + "(" + Util.displayString(mbWritten) + "MB written in "
                        + Util.displayString(sec) + " sec)");
                App.updateMetrics(wMark);

                /*
                  Let the GUI know the interim result described by the current Mark
                 */
                pu.publishpi(wMark);

                // Keep track of statistics to be displayed and persisted after all Marks are done.
                run.setRunMax(wMark.getCumMax());
                run.setRunMin(wMark.getCumMin());
                run.setRunAvg(wMark.getCumAvg());
                run.setEndTime(new Date());
            } // END outer loop for specified duration (number of 'marks') for WRITE benchmark

            /*
              Persist info about the Write BM Run (e.g. into Derby Database) and add it to a GUI panel
             */
            EntityManager em = EM.getEntityManager();
            em.getTransaction().begin();
            em.persist(run);
            em.getTransaction().commit();

            Gui.runPanel.addRun(run);

            }
}

