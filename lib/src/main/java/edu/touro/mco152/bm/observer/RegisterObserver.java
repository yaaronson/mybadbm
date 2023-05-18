package edu.touro.mco152.bm.observer;

import edu.touro.mco152.bm.persist.DiskRun;

public interface RegisterObserver {

     void registerObserver(ObserverBenchmark observer);

     void unregisterObserver(ObserverBenchmark observer);

     void notifyObserver(DiskRun run);
}
