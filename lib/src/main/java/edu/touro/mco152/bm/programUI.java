package edu.touro.mco152.bm;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * This interface defines the methods for a program's user interface.
 */

public interface programUI {

    /**
     The doInBackgroundpi method performs the background computation and returns a Boolean value.
     @throws Exception if there is an error.
     */
    Boolean doInBackgroundpi() throws Exception;

    /**
     * processes a list of DiskMark objects
     * @param markList the list of DiskMark objects
     */
    void processpi(List<DiskMark> markList);

    /**
     * This method is called when the background thread is finished.
     */
    void donepi();

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
     *
     * @param message the messages to output
     */
    void message(String message);


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
