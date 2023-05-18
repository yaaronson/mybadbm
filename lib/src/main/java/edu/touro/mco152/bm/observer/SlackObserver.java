package edu.touro.mco152.bm.observer;

import edu.touro.mco152.bm.persist.DiskRun;

public class SlackObserver implements ObserverBenchmark{

    @Override
    public void sendNotification(DiskRun run) {
        if (run.getRunMax() > (run.getRunAvg() * 1.03)){
            SlackManager slack = new SlackManager("Benchmark Channel");
            slack.postMsg2OurChannel("This is the benchmark message"); //boolean?
        }

    }
}
