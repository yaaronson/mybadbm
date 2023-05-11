package edu.touro.mco152.bm.commands;

import edu.touro.mco152.bm.persist.DiskRun;

/**
 * The CmdBenchmark interface represents a benchmark command
 */
public interface CmdBenchmark {

    /**
     * Executes the benchmark command, Inheriting classes should provide logic for executing
     * the benchmark
     */
    void execute();

}

