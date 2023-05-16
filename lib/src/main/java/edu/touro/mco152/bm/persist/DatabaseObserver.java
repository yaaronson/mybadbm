package edu.touro.mco152.bm.persist;

import edu.touro.mco152.bm.observer.ObserverBenchmark;
import jakarta.persistence.EntityManager;

public class DatabaseObserver implements ObserverBenchmark {

    @Override
    public void sendNotification(DiskRun run) {
        EntityManager em = EM.getEntityManager();
        em.getTransaction().begin();
        em.persist(run);
        em.getTransaction().commit();
    }

}
