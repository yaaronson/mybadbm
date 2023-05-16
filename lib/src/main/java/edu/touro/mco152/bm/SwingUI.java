package edu.touro.mco152.bm;

import edu.touro.mco152.bm.persist.DiskRun;
import edu.touro.mco152.bm.persist.EM;
import edu.touro.mco152.bm.ui.Gui;
import jakarta.persistence.EntityManager;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static edu.touro.mco152.bm.App.*;
import static edu.touro.mco152.bm.DiskMark.MarkType.READ;
import static edu.touro.mco152.bm.DiskMark.MarkType.WRITE;

    /**
    * A SwingUI class that extends SwingWorker and implements programUI.
    * This class handles the GUI updates and interactions during the benchmark process.
     */

public class SwingUI extends SwingWorker<Boolean, DiskMark> implements programUI  {


    Boolean lastStatus = null;

    /**
     * Receives the results from the background thread and updates the GUI accordingly.
     * @param markList the list of DiskMark objects to process
     */
    @Override
    public void process(List<DiskMark> markList){
        markList.stream().forEach((dm) -> {
            if (dm.type == DiskMark.MarkType.WRITE) {
                Gui.addWriteMark(dm);
            } else {
                Gui.addReadMark(dm);
            }
        });
    }

    /**
     * This method is called when the background thread is finished.
     * Logs a message if exception thrown
     */

    @Override
    public void done() {
        // Obtain final status, might from doInBackground ret value, or SwingWorker error
        try {
            lastStatus = super.get();   // record for future access
        } catch (Exception e) {
            Logger.getLogger(App.class.getName()).warning("Problem obtaining final status: " + e.getMessage());
        }

        if (App.autoRemoveData) {
            Util.deleteDirectory(dataDir);
        }
        App.state = App.State.IDLE_STATE;
        Gui.mainFrame.adjustSensitivity();
    }


    /**
     * @return true if the SwingWorker has been cancelled, false otherwise
     */

    @Override
    public boolean isCancelledpi() {
        return isCancelled();
    }

    /**
     * Sets the progress of the SwingWorker to the specified value.
     * @param percentComplete the percentage of the task that is complete
     */
    @Override
    public void setProgresspi(int percentComplete) {
            setProgress(percentComplete);
    }

    /**
     * Publishes a single result to any attached ProcessListener.
     * @param wMark the DiskMark to publish
     */
    @Override
    public void publishpi(DiskMark wMark) {

        publish(wMark);
    }

    /**
     * Returns the progress of the SwingWorker.
     *
     * @return the progress of the SwingWorker, as a percentage from 0 to 100
     */

    @Override
    public int getProgresspi() {
        return getProgress();
    }

    /**
     * Adds a PropertyChangeListener to the SwingWorker.
     *
     * @param event the PropertyChangeListener to add
     */
    @Override
    public void addPropertyChangeListenerpi(PropertyChangeListener event) {
        addPropertyChangeListener(event);
    }

    /**
     * Executes the SwingWorker. This method starts the background thread that
     * runs the task defined in the doInBackground() method.
     */
    @Override
    public void executepi() {
        execute();
    }

    /**
     * This method cancels the background thread that
     * runs the task defined in the doInBackground() method. The boolean argument
     * determines whether the thread is interrupted or allowed to complete
     *
     * @param b true to interrupt the thread, false to let it complete
     */
    @Override
    public void cancelpi(boolean b) {
        cancel(b);
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * <p>
     * Note that this method is executed only once.
     *
     * <p>
     * Note: this method is executed in a background thread.
     *
     * @return the computed result
     * @throws Exception if unable to compute a result
     */

    @Override
    protected Boolean doInBackground() throws Exception {
        return worker.doInBackground();
    }

}


