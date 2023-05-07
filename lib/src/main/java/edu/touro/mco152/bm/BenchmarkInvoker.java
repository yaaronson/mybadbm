package edu.touro.mco152.bm;

import edu.touro.mco152.bm.persist.DiskRun;

public class BenchmarkInvoker {

    public CmdBenchmark cmdread;
    public CmdBenchmark cmdwrite;


    public BenchmarkInvoker(programUI pu) {
        this.cmdread = new ReadBenchmark(pu);
        this.cmdwrite = new WriteBenchmark(pu);

    }

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
