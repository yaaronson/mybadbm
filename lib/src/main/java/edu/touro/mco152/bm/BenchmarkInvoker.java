package edu.touro.mco152.bm;

public class BenchmarkInvoker {

    public CmdBenchmark cmdread;
    public CmdBenchmark cmdwrite;


    public BenchmarkInvoker(programUI pu) {
        this.cmdread = new ReadBenchmark(pu);
        this.cmdwrite = new WriteBenchmark(pu);

    }

    public void executor(boolean doRead){
        if (doRead){
            cmdread.execute();
        } else {
            cmdwrite.execute();
        }
    }
}
