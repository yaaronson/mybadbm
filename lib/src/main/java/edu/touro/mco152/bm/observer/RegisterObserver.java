package edu.touro.mco152.bm.observer;

import edu.touro.mco152.bm.persist.DiskRun;

/**
 * This interface defines the methods for registering, unregistering, and notifying observers
 */
public interface RegisterObserver {

     /**
      * Registers our observers
      * @param observer
      */
     void registeringObserver(ObserverBenchmark observer);

     /**
      * Unregisters our observes
      * @param observer
      */
     void unregisterObserver(ObserverBenchmark observer);

     /**
      * Notifies the observers
      * @param run
      */
     void notifyObserver(DiskRun run);
}
