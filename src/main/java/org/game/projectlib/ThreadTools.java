package org.game.projectlib;

public class ThreadTools {
    public static void sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void runDaemon(String threadName, Runnable task){
        Thread thread = new Thread(task);
        thread.setName(threadName);
        thread.setDaemon(true);
        thread.start();
    }

    public static void runAsyncTask(Runnable task){
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}
