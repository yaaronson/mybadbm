package edu.touro.mco152.bm.commands;

import edu.touro.mco152.bm.persist.DiskRun;
import edu.touro.mco152.bm.programUI;

/**
 * This class represents a class for invoking benchmark commands, our Invoker/executor
 */
public class BenchmarkInvoker {

    public CmdBenchmark cmdread;
    public CmdBenchmark cmdwrite;

    /**
     * Constructor
     * @param pu
     */
    public BenchmarkInvoker(programUI pu) {
        this.cmdread = new ReadBenchmark(pu);
        this.cmdwrite = new WriteBenchmark(pu);

    }

    /**
     * Executes the benchmark command based off the parameters
     * @param doRead the boolean flag for reading
     * @param numOfBlocks the number of blocks
     * @param numOfMarks the number of marks
     * @param blockSizeKb the size of block in kilobytes
     * @param blockSequence the block sequence
     */
    public void executor(boolean doRead, int numOfBlocks, int numOfMarks,int blockSizeKb,
                         DiskRun.BlockSequence blockSequence){
        if (doRead){
            cmdread.execute( numOfBlocks,  numOfMarks, blockSizeKb,
            blockSequence);
        } else {
            cmdwrite.execute(numOfBlocks,  numOfMarks, blockSizeKb,
                    blockSequence);
        }
    }
}
