package edu.touro.mco152.bm.commands;

import edu.touro.mco152.bm.persist.DiskRun;

/**
 * Interface for benchmarking disk performance.
 */
public interface CmdBenchmark {

    /**
     * Executes the disk benchmark with the given parameters
     */
    public void execute();

}

