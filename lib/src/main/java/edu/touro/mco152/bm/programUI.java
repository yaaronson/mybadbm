package edu.touro.mco152.bm;

import java.util.List;

/**
 *
 */

public interface programUI {
    Boolean doInBackgroundpi() throws Exception;

    void processpi(List<DiskMark> markList);

    void donepi();

    boolean isCancelledpi();

    void setProgresspi(int percentComplete);

    void publishpi(DiskMark wMark);

    //Boolean getpi();
}
