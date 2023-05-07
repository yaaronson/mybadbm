package edu.touro.mco152.bm;

import edu.touro.mco152.bm.ui.Gui;
import edu.touro.mco152.bm.ui.MainFrame;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Properties;

import static edu.touro.mco152.bm.persist.DiskRun.BlockSequence.SEQUENTIAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Implementation of Command Pattern Unit Tests
 */
public class CMDTest {

    programUI pi = new TestUI();

    /**
     * Bruteforce setup of static classes/fields to allow DiskWorker to run.
     *
     * @author lcmcohen
     */
    private void setupDefaultAsPerProperties()
    {
        /// Do the minimum of what  App.init() would do to allow torun.
        App.worker = new DiskWorker(pi);
        Gui.mainFrame = new MainFrame();
        App.p = new Properties();
        App.loadConfig();
        System.out.println(App.getConfigString());
        Gui.progressBar = Gui.mainFrame.getProgressBar(); //must be set or get Nullptr

        // configure the embedded DB in .jDiskMark
        System.setProperty("derby.system.home", App.APP_CACHE_DIR);

        // code from startBenchmark
        //4. create data dir reference
        App.dataDir = new File(App.locationDir.getAbsolutePath()+ File.separator+App.DATADIRNAME);

        //5. remove existing test data if exist
        if (App.dataDir.exists()) {
            if (App.dataDir.delete()) {
                App.msg("removed existing data dir");
            } else {
                App.msg("unable to remove existing data dir");
            }
        }
        else
        {
            App.dataDir.mkdirs(); // create data dir if not already present
        }
    }

    BenchmarkInvoker bi = new BenchmarkInvoker(pi);

    @Test
    void ReadBenchmark() {
        setupDefaultAsPerProperties();
        bi.executor(true,25, 128,2048, SEQUENTIAL);
        assertEquals(100, pi.getProgresspi());
    }

    @Test
    void WriteBenchmark() {
        setupDefaultAsPerProperties();
        bi.executor(false,25, 128,2048, SEQUENTIAL);
        assertEquals(100, pi.getProgresspi());
    }

}