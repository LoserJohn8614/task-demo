package com.wengu.task.demo.jdk;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author LoserJohn
 * @create 2020-11-20 18:34
 */
@Component
public class ScheduledDemo {
    @Scheduled(cron = "1/25 * * * * *")
    public void test(){
        System.out.println("这是spring的定时任务\t"+System.currentTimeMillis() );
    }
}
