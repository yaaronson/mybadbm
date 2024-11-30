package edu.touro.mco152.bm.commands;

import edu.touro.mco152.bm.observer.RegisterObserver;

/**
 * The CmdBenchmark interface represents a benchmark command
 */
public interface CmdBenchmark {

    /**
     * Executes the benchmark command, Inheriting classes will provide their own implementation
     * @return true if successful and false if not
     */
    boolean execute(RegisterObserver register);

}

