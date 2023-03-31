package edu.touro.mco152.bm;

import edu.touro.mco152.bm.ui.Gui;
import edu.touro.mco152.bm.ui.MainFrame;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Implementation of ProgramUI interface
 *
 * @author Y Aaronson
 */

class BadbmTest implements programUI{


    int progress = 0;

    @Override
    public Boolean doInBackgroundpi() throws Exception {
        return App.worker.doInBackground();
    }

    @Override
    public void processpi(List<DiskMark> markList) {

    }

    @Override
    public void donepi() {

    }

    @Override
    public boolean isCancelledpi() {
        return false;
    }

    @Override
    public void setProgresspi(int percentComplete) {
            progress = percentComplete;
    }

    @Override
    public void publishpi(DiskMark wMark) {

    }

    @Override
    public void message(String message) {
        System.out.println(message);
    }

    @Override
    public int getProgresspi() {
        return progress;
    }

    @Override
    public void cancelpi(boolean b) {

    }

    @Override
    public void addPropertyChangeListenerpi(PropertyChangeListener event) {

    }

    @Override
    public void executepi() {

    }

}