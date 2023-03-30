package edu.touro.mco152.bm;

import java.util.List;

/**
 * This interface defines the methods for a program's user interface.
 */

public interface programUI {
    Boolean doInBackgroundpi() throws Exception;

    void processpi(List<DiskMark> markList);

    void donepi();

    boolean isCancelledpi();

    void setProgresspi(int percentComplete);

    void publishpi(DiskMark wMark);

    void message(String message);

    int getProgresspi();
}
