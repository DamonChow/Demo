package com.damon.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 功能：
 *
 * @author Damon
 * @since 2018-11-29 16:38
 */
@Component
public class MemberTask {

//    @Scheduled(cron = "0/5 * * * * ?")
    public void job() {
        System.out.println("123");
    }
}
