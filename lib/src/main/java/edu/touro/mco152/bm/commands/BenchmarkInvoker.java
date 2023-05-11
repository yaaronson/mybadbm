package edu.touro.mco152.bm.commands;

import edu.touro.mco152.bm.persist.DiskRun;
import edu.touro.mco152.bm.programUI;

/**
 * This class represents a class for invoking benchmark commands, our Invoker/executor
 */
public class BenchmarkInvoker {

    /**
     * This method invokes the execute method of the provided command to execute the benchmark
     * @param cmd the benchmark to be executed
     */
    public void executor(CmdBenchmark cmd){
            cmd.execute();
        }

}
