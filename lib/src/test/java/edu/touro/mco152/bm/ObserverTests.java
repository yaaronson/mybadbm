package edu.touro.mco152.bm;

import edu.touro.mco152.bm.commands.BenchmarkInvoker;
import edu.touro.mco152.bm.commands.ReadBenchmark;
import edu.touro.mco152.bm.ui.Gui;
import edu.touro.mco152.bm.ui.MainFrame;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Properties;

import static edu.touro.mco152.bm.persist.DiskRun.BlockSequence.SEQUENTIAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests our Observer
 */
public class ObserverTests {

        static programUI pi = new TestUI();

        static BenchmarkInvoker bi = new BenchmarkInvoker();

        /**
         * Bruteforce setup of static classes/fields to allow DiskWorker to run.
         *
         * @author lcmcohen
         */
        private static void setupDefaultAsPerProperties ()
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
            App.dataDir = new File(App.locationDir.getAbsolutePath() + File.separator + App.DATADIRNAME);

            //5. remove existing test data if exist
            if (App.dataDir.exists()) {
                if (App.dataDir.delete()) {
                    App.msg("removed existing data dir");
                } else {
                    App.msg("unable to remove existing data dir");
                }
            } else {
                App.dataDir.mkdirs(); // create data dir if not already present
            }
        }

    /**
     * Tests our observers, sets a flag that the observer was invoked
     */
    @AfterAll
    @Test
     static void RegisterObserver() {
        setupDefaultAsPerProperties();
        ChecksObserver register = new ChecksObserver();
        bi.registeringObserver(register);
        bi.executor(new ReadBenchmark(pi, 25, 128,2048, SEQUENTIAL));
        assertEquals(100, pi.getProgresspi());
        assertTrue(register.observer);
    }
    @Test
     void TestObservers() {
        setupDefaultAsPerProperties();
        ChecksObserver register = new ChecksObserver();
        bi.registeringObserver(register);
        bi.executor(new ReadBenchmark(pi, 25, 128,2048, SEQUENTIAL));
        assertEquals(100, pi.getProgresspi());
        assertTrue(register.observer);
    }

    }

