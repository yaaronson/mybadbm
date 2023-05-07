package edu.touro.mco152.bm;

import java.beans.PropertyChangeListener;

/**
 * Implementation of ProgramUI interface
 *
 * @author Y Aaronson
 */

class TestUI implements programUI{


    int progress = 0;

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