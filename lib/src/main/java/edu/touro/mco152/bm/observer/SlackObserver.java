package edu.touro.mco152.bm.observer;

import edu.touro.mco152.bm.commands.ReadBenchmark;
import edu.touro.mco152.bm.persist.DiskRun;

/**
 * Slack Observer class, implements Observer Benchmark Interface
 */
public class SlackObserver implements ObserverBenchmark{

    @Override
    public void sendNotification(DiskRun run) {
        if ((run.getIoMode() == DiskRun.IOMode.READ || run.getIoMode() == DiskRun.IOMode.READ_WRITE) && run.getRunMax() > (run.getRunAvg() * 1.03)) {
            SlackManager slack = new SlackManager("Benchmark Channel");
            slack.postMsg2OurChannel("Read benchmark result has an iteration 'max time' that exceeds 3 per cent of the benchmarks average time");
        }

    }
}
