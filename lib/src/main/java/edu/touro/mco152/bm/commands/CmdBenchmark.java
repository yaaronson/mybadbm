package edu.touro.mco152.bm.commands;

import edu.touro.mco152.bm.persist.DiskRun;

/**
 * Interface for benchmarking disk performance.
 */
public interface CmdBenchmark {

    /**
     * Executes the disk benchmark with the given parameters
     * @param numOfBlocks The number of blocks to be used in the benchmark
     * @param numOfMarks The number of times to repeat the benchmark
     * @param blockSizeKb The size of each block in kilobytes
     * @param blockSequence The sequence in which to access the blocks
     */
    public void execute(int numOfBlocks, int numOfMarks, int blockSizeKb,
                        DiskRun.BlockSequence blockSequence);

}

