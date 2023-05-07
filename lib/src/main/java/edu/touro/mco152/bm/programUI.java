package edu.touro.mco152.bm;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * This interface defines the methods for a program's user interface.
 */

public interface programUI {


    /**
     * Checks if the task has been cancelled.
     * @return true if cancelled, false if not
     */
    boolean isCancelledpi();

    /**
     * Sets the progress of the task.
     * @param percentComplete
     */
    void setProgresspi(int percentComplete);

    /**
     * Publishes a disk mark for further processing.
     * @param wMark
     */
    void publishpi(DiskMark wMark);


    /**
     * Gets the progress of the task.
     * @return
     */
    int getProgresspi();


    /**
     * Cancels the task.
     * @param b
     */
    void cancelpi(boolean b);


    /**
     * Adds a property change listener to the task.
     * @param event
     */
    void addPropertyChangeListenerpi(PropertyChangeListener event);


    /**
     * Executes the tasks
     */
     void executepi();
}
