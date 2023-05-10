package edu.touro.mco152.bm.commands;

import edu.touro.mco152.bm.persist.DiskRun;
import edu.touro.mco152.bm.programUI;

/**
 * This class represents a class for invoking benchmark commands, our Invoker/executor
 */
public class BenchmarkInvoker {


    /**
     * Executes the benchmark command based off the parameters
     */
    public void executor(CmdBenchmark cmd){
            cmd.execute();
        }

}
