//package edu.touro.mco152.bm;
//
//import edu.touro.mco152.bm.ui.Gui;
//import edu.touro.mco152.bm.ui.MainFrame;
//import org.junit.jupiter.api.Test;
//
//import java.io.File;
//import java.util.List;
//import java.util.Properties;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class BadbmTest implements programUI{
//
//    SwingUI si = new SwingUI();
//
//    /**
//     * Bruteforce setup of static classes/fields to allow DiskWorker to run.
//     *
//     * @author lcmcohen
//     */
//    private void setupDefaultAsPerProperties()
//    {
//        /// Do the minimum of what  App.init() would do to allow to run.
//        Gui.mainFrame = new MainFrame();
//        App.p = new Properties();
//        App.loadConfig();
//        System.out.println(App.getConfigString());
//        Gui.progressBar = Gui.mainFrame.getProgressBar(); //must be set or get Nullptr
//
//        // configure the embedded DB in .jDiskMark
//        System.setProperty("derby.system.home", App.APP_CACHE_DIR);
//
//        // code from startBenchmark
//        //4. create data dir reference
//        App.dataDir = new File(App.locationDir.getAbsolutePath()+ File.separator+App.DATADIRNAME);
//
//        //5. remove existing test data if exist
//        if (App.dataDir.exists()) {
//            if (App.dataDir.delete()) {
//                App.msg("removed existing data dir");
//            } else {
//                App.msg("unable to remove existing data dir");
//            }
//        }
//        else
//        {
//            App.dataDir.mkdirs(); // create data dir if not already present
//        }
//    }
//
////    @Test
////    void doInBackground() throws Exception {
////        Boolean result = si.doInBackground();
////        assertNotNull(result);
////    }
//
//    @Override
//    public Boolean doInBackgroundpi() throws Exception {
//        return null;
//    }
//
//    @Override
//    public void processpi(List<DiskMark> markList) {
//
//    }
//
//    @Override
//    public void donepi() {
//
//    }
//
//    @Override
//    public boolean isCancelledpi() {
//        return false;
//    }
//
//    @Override
//    public void setProgresspi(int percentComplete) {
//
//    }
//
//    @Override
//    public void publishpi(DiskMark wMark) {
//
//    }
//
//    @Override
//    public Boolean getpi() {
//        return null;
//    }
//
//
//}