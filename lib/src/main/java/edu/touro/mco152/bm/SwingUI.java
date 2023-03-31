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

public class SwingUI extends SwingWorker<Boolean, DiskMark> implements programUI  {


    Boolean lastStatus = null;

    @Override
    public Boolean doInBackgroundpi() throws Exception {
        return  App.worker.doInBackground();
    }

    /**
     * Processes a list of DiskMark objects by adding them to the right list in the Gui.
     * @param markList a List of DiskMark objects to process
     */
    @Override
    public void processpi(List<DiskMark> markList) {
        markList.stream().forEach((dm) -> {
            if (dm.type == DiskMark.MarkType.WRITE) {
                Gui.addWriteMark(dm);
            } else {
                Gui.addReadMark(dm);
            }
        });
    }

    /**
     * Called by the SwingWorker when it's done processing the work in doInBackground(). It obtains the final status
     * and records it for future access.
     * Logs a message if exception thrown
     */

    @Override
    public void donepi() {
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



    @Override
    public boolean isCancelledpi() {
        return isCancelled();
    }

    @Override
    public void setProgresspi(int percentComplete) {
            setProgress(percentComplete);
    }

    @Override
    public void publishpi(DiskMark wMark) {
         publish();
    }

    @Override
    public void message(String message) {
        Gui.mainFrame.msg(message);
    }

    @Override
    public int getProgresspi() {
        return getProgress();
    }

    @Override
    public void addPropertyChangeListenerpi(PropertyChangeListener event) {
        addPropertyChangeListener(event);
    }

    @Override
    public void executepi() {
        execute();
    }

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
        return doInBackgroundpi();
    }

}


