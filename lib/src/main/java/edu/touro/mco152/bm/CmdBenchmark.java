package edu.touro.mco152.bm;

import edu.touro.mco152.bm.persist.DiskRun;

public interface CmdBenchmark {

    public void execute(int numOfBlocks, int numOfMarks,int blockSizeKb,
                        DiskRun.BlockSequence blockSequence);

}
