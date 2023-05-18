package edu.touro.mco152.bm;

import edu.touro.mco152.bm.observer.ObserverBenchmark;
import edu.touro.mco152.bm.persist.DiskRun;

public class ChecksObserver implements ObserverBenchmark {


    boolean observer = false;

    @Override
    public void sendNotification(DiskRun run) {
        observer = true;
    }
}
