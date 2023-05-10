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

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static edu.touro.mco152.bm.App.*;
import static edu.touro.mco152.bm.App.KILOBYTE;
import static edu.touro.mco152.bm.DiskMark.MarkType.READ;

/**
 * This class implements the CmdBenchmark interface and executes the read
 * benchmark. It initializes local variables that keeps track of benchmarks and a large read/write
 * buffer. It also sets up the DiskRun object and the DiskMark object that will be used to pass
 * progress to the user interface.
 */
public class ReadBenchmark implements CmdBenchmark {

    programUI pu;
    int numOfBlocks;
    int numOfMarks;
    int blockSizeKb;
    DiskRun.BlockSequence blockSequence;

    public ReadBenchmark( programUI pu, int numOfBlocks, int numOfMarks, int blockSizeKb,
                           DiskRun.BlockSequence blockSequence) {
        this.pu = pu;
        this.numOfBlocks = numOfBlocks;
        this.numOfMarks = numOfMarks;
        this.blockSizeKb = blockSizeKb;
        this.blockSequence = blockSequence;
    }

    /**
     * This method reads from the test file, measures the read throughput, and updates the user interface with the progress.
     */
    @Override
    public void execute(){

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

    DiskMark rMark;  // declare vars that will point to objects used to pass progress to UI

    Gui.updateLegend();  // init chart legend info

    if (App.autoReset) {
        App.resetTestData();
        Gui.resetTestData();
    }

    int startFileNum = App.nextMarkNumber;


        DiskRun run = new DiskRun(DiskRun.IOMode.READ, App.blockSequence);
        run.setNumMarks(numOfMarks);
        run.setNumBlocks(numOfBlocks);
        run.setBlockSize(blockSizeKb);
        run.setTxSize(App.targetTxSizeKb());
        run.setDiskInfo(Util.getDiskInfo(dataDir));

        msg("disk info: (" + run.getDiskInfo() + ")");

        Gui.chartPanel.getChart().getTitle().setVisible(true);
        Gui.chartPanel.getChart().getTitle().setText(run.getDiskInfo());

        for (int m = startFileNum; m < startFileNum + numOfMarks && !pu.isCancelledpi(); m++) {

            if (App.multiFile) {
                testFile = new File(dataDir.getAbsolutePath()
                        + File.separator + "testdata" + m + ".jdm");
            }
            rMark = new DiskMark(READ);  // starting to keep track of a new benchmark
            rMark.setMarkNum(m);
            long startTime = System.nanoTime();
            long totalBytesReadInMark = 0;

            try {
                try (RandomAccessFile rAccFile = new RandomAccessFile(testFile, "r")) {
                    for (int b = 0; b < numOfBlocks; b++) {
                        if (blockSequence == DiskRun.BlockSequence.RANDOM) {
                            int rLoc = Util.randInt(0, numOfBlocks - 1);
                            rAccFile.seek((long) rLoc * blockSize);
                        } else {
                            rAccFile.seek((long) b * blockSize);
                        }
                        rAccFile.readFully(blockArr, 0, blockSize);
                        totalBytesReadInMark += blockSize;
                        rUnitsComplete++;
                        unitsComplete = rUnitsComplete + wUnitsComplete;
                        percentComplete = (float) unitsComplete / (float) unitsTotal * 100f;
                        pu.setProgresspi((int) percentComplete);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                String emsg = "May not have done Write Benchmarks, so no data available to read." +
                        ex.getMessage();
                JOptionPane.showMessageDialog(Gui.mainFrame, emsg, "Unable to READ", JOptionPane.ERROR_MESSAGE);
                msg(emsg);
                return;
            }
            long endTime = System.nanoTime();
            long elapsedTimeNs = endTime - startTime;
            double sec = (double) elapsedTimeNs / (double) 1000000000;
            double mbRead = (double) totalBytesReadInMark / (double) MEGABYTE;
            rMark.setBwMbSec(mbRead / sec);
            msg("m:" + m + " READ IO is " + rMark.getBwMbSec() + " MB/s    "
                    + "(MBread " + mbRead + " in " + sec + " sec)");
            App.updateMetrics(rMark);
            pu.publishpi(rMark);

            run.setRunMax(rMark.getCumMax());
            run.setRunMin(rMark.getCumMin());
            run.setRunAvg(rMark.getCumAvg());
            run.setEndTime(new Date());
        }

            /*
              Persist info about the Read BM Run (e.g. into Derby Database) and add it to a GUI panel
             */
        EntityManager em = EM.getEntityManager();
        em.getTransaction().begin();
        em.persist(run);
        em.getTransaction().commit();

        Gui.runPanel.addRun(run);

    }

}
