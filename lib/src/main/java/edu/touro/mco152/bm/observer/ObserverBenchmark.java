package edu.touro.mco152.bm.observer;

import edu.touro.mco152.bm.persist.DiskRun;

/**
 * An interface for receiving notifications for a benchmark
 */
public interface ObserverBenchmark {

    /**
     * Sends a notification after the benchmark
     *
     * @param run The disk run object
     */
    void sendNotification(DiskRun run);
}

