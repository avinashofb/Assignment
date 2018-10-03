package com.OfbAssignment;

import java.security.PublicKey;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.concurrent.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.awt.Mutex;

public class Main {

    private final static int QUEUE_SIZE = 50;
    private final static int THREAD_POOL_SIZE = 4;
    //ID's
    private final static String[] ID = {"A", "B", "C", "D"};

    public static HashMap<String, Semaphore> keysMap = new HashMap<>();
    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(WorkerThread.class);

        initFlagMap();

        ExecutorService taskQueueThreadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        WorkerThread workerThread;
        int randomID = 0;
        for (int i = 0; i < QUEUE_SIZE; i++) {
            //Getting Random ID on Task 
            randomID = ThreadLocalRandom.current().nextInt(0, 4);
            //Assigning ID and Sequence No
            workerThread = new WorkerThread("" + i, ID[randomID]);
            taskQueueThreadPool.execute(workerThread);
        }
        taskQueueThreadPool.shutdown();
        try {
            taskQueueThreadPool.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            logger.error("InterruptedException - ", new Exception(e.getMessage()));
        }
    }

    private static void initFlagMap() {
        Semaphore semaphoreA = new Semaphore(1);
        Semaphore semaphoreB = new Semaphore(1);
        Semaphore semaphoreC = new Semaphore(1);
        Semaphore semaphoreD = new Semaphore(1);

        keysMap.put(ID[0],semaphoreA);
        keysMap.put(ID[1],semaphoreB);
        keysMap.put(ID[2],semaphoreC);
        keysMap.put(ID[3],semaphoreD);
    }


    public static class WorkerThread implements Runnable {

        private Logger logger = LoggerFactory.getLogger(WorkerThread.class);
        private String sequenceNo;
        private String id;

        public WorkerThread(String sequenceNo, String id) {
            this.sequenceNo = sequenceNo;
            this.id = id;
        }

        NumberFormat formatter;
        long startingTime;
        long endingTime;

        @Override
        public void run() {

            Semaphore semaphore = keysMap.get(id);

            try {

                //Locking thread
                semaphore.acquire();

                //Starting Time
                startingTime = System.currentTimeMillis();

                logger.info("Thread Name ->" + Thread.currentThread().getName() + "    Started...       Sequence number => " + sequenceNo + " | TASK ID => " + id);

                //Random Sleep between 0 to 5 second
                Thread.currentThread().sleep(ThreadLocalRandom.current().nextInt(0, 5000 + 1));
                //Ending Time
                endingTime = System.currentTimeMillis();
                formatter = new DecimalFormat("#0.00000");

                //Logging the Thread Status
                logger.info(Thread.currentThread().getName() + "      Ended...      Sequence number => " + sequenceNo + " | Time taken => " + formatter.format((endingTime - startingTime) / 1000d) + " seconds |" + "TASK ID => " + id);
                //Releasing Lock
                semaphore.release();

            } catch (InterruptedException e) {
                logger.error("InterruptedException - ", new Exception(e.getMessage()));
            } finally {
                //Releasing Lock
                semaphore.release();
            }

        }
    }
}
