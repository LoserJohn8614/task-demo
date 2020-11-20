package com.wengu.task.demo.jdk;

import ch.qos.logback.core.util.TimeUtil;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 自定义延时任务
 * @author LoserJohn
 * @create 2020-11-20 16:31
 */
public class TimerDemo {
    public static void main(String[] args) {
        System.out.println("这是初始化时的系统时间： " + System.currentTimeMillis());
        new DelayTask().scheduled(() -> {
            System.out.println("这是自定义的定时任务，执行时间：" + System.currentTimeMillis());
        }, 5l, 10l, TimeUnit.SECONDS);
    }
}

class DelayTask implements Delayed {

    private long timeout;//任务执行时间

    private DelayQueue<DelayTask> delayQueue = new DelayQueue<>();

    /**
     * return值小于等于0时该任务执行
     *
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return this.timeout - System.currentTimeMillis();
    }

    /**
     * 排序方法
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Delayed o) {
        return (int) (this.timeout - ((DelayTask) o).timeout);
    }

    /**
     * @param task     执行的任务
     * @param delay    延迟时间
     * @param timeUtil 时间单位
     */
    public void scheduled(Runnable task, Long delay, TimeUnit timeUtil) {
        this.timeout = System.currentTimeMillis() + timeUtil.toMillis(delay);
        try {
            delayQueue.put(this);
            delayQueue.take();
            new Thread(task).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param task     执行任务
     * @param delay    延迟时间
     * @param period   执行周期
     * @param timeUtil 时间单位
     */
    public void scheduled(Runnable task, Long delay, Long period, TimeUnit timeUtil) {
        this.timeout = System.currentTimeMillis() + timeUtil.toMillis(delay);
        try {
            while (true) {
                delayQueue.put(this);
                delayQueue.take();
                new Thread(task).start();
                this.timeout = System.currentTimeMillis() + timeUtil.toMillis(period);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
