package edu.touro.mco152.bm;

import edu.touro.mco152.bm.ui.Gui;
import edu.touro.mco152.bm.ui.MainFrame;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Implementation of unit tests
 *
 * @author Y Aaronson
 */

public class BadBMTest {

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

    @Test
    void doInBackground() throws Exception {
        setupDefaultAsPerProperties();
        Boolean result = App.worker.doInBackground();
        assertNotNull(result);
    }

    @Test
    void setProgressTest() throws Exception {
        setupDefaultAsPerProperties();
        Boolean result = App.worker.doInBackground();
        assertEquals(100, pi.getProgresspi());
    }

    @Test
    void setProgressPI() throws Exception {
        setupDefaultAsPerProperties();
        pi.setProgresspi(87);
        assertEquals(87, pi.getProgresspi());
    }

}
