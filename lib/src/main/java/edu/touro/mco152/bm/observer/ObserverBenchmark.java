package edu.touro.mco152.bm.observer;

import edu.touro.mco152.bm.persist.DiskRun;

public interface ObserverBenchmark {

    void sendNotification(DiskRun run);
}
