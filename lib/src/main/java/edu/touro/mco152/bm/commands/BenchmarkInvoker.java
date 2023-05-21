package edu.touro.mco152.bm.commands;

import edu.touro.mco152.bm.observer.ObserverBenchmark;
import edu.touro.mco152.bm.observer.RegisterObserver;
import edu.touro.mco152.bm.persist.DiskRun;
import edu.touro.mco152.bm.programUI;
import java.util.ArrayList;


/**
 * This class represents a class for invoking benchmark commands, our Invoker/executor
 */
public class BenchmarkInvoker implements RegisterObserver {

    /**
     * This method invokes the execute method of the provided command to execute the benchmark
     * @param cmd the benchmark to be executed
     */
    public boolean executor(CmdBenchmark cmd){

        return cmd.execute(this);
        }


    ArrayList<ObserverBenchmark> list = new ArrayList<>();

    /**
     * Registers Observers
     * @param observer
     */
    public void registeringObserver(ObserverBenchmark observer){
        list.add(observer);
    }

    /**
     * Unregisters Observers
     * @param observer
     */
    public void unregisterObserver(ObserverBenchmark observer){
        list.remove(observer);
    }

    /**
     * Notifies the observers
     * @param run
     */
    public void notifyObserver(DiskRun run){
        for (int i = 0; i < list.size(); i++){
            list.get(i).sendNotification(run);
        }
    }


}
