package com.hht.crestronserivce.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Realmo
 * @version 1.0.0
 * @name CrestronThreadPool
 * @email momo.weiye@gmail.com
 * @time 2019/4/28 11:48
 * @describe
 */
public class CrestronThreadPool extends ThreadPoolExecutor {

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    // one service thread  + two client thread
    private static final int INIT_THREAD_COUNT = 3;
    private static final int MAX_THREAD_COUNT = CPU_COUNT+1;

    private static CrestronThreadPool instance;

    public  static CrestronThreadPool getInstance(){
        if (null == instance) {
            synchronized (CrestronThreadPool.class) {
                if (null == instance) {
                    instance = new CrestronThreadPool(
                            INIT_THREAD_COUNT,
                            MAX_THREAD_COUNT,
                            0L,
                            TimeUnit.MILLISECONDS,
                            new ArrayBlockingQueue<Runnable>(8)
                            );
                }
            }
        }
        return instance;
    }

    private CrestronThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }


    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        DefaultLogger.debug("beforeExecute:"+r.toString());
    }

    /*
     *  线程执行结束，顺便看一下有么有什么乱七八糟的异常
     *
     * @param r the runnable that has completed
     * @param t the exception that caused termination, or null if
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        DefaultLogger.debug("afterExecute:"+r.toString());
        if (t == null && r instanceof Future<?>) {
            try {
                ((Future<?>) r).get();
            } catch (CancellationException ce) {
                t = ce;
            } catch (ExecutionException ee) {
                t = ee.getCause();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt(); // ignore/reset
            }
        }
        if (t != null) {
            DefaultLogger.warning("Running task appeared exception! Thread [" + Thread.currentThread().getName() + "], because [" + t.getMessage() + "]\n"
                    +
                    DefaultLogger.getExtInfo(t.getStackTrace()));
        }
    }
}
