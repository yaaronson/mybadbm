package edu.touro.mco152.bm.commands;

import edu.touro.mco152.bm.persist.DiskRun;

/**
 * The CmdBenchmark interface represents a benchmark command
 */
public interface CmdBenchmark {

    /**
     * Executes the benchmark command, Inheriting classes will provide their own implementation
     */
    void execute();

}

