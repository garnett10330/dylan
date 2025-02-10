package com.momo.dylantest.task;

import com.momo.dylantest.util.DateTimeUtil;
import com.momo.dylantest.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
/**
 * 盡量使用cron表達式
 * */
@EnableScheduling
@Component
@Slf4j
public class TestTask {
    // 每 10 秒執行一次
    //@Scheduled(fixedRate = 10000)
    public void executeTaskWithFixedRate() {
        log.info(LogUtil.info(LogUtil.GATE_OTHER,"fixedRateTask", DateTimeUtil.getCurrentDateTimeStr()));
    }
    // 每 10 秒執行一次
    //@Scheduled(cron = "0/10 * * * * ?")
    public void executeTaskWithCron() {
        log.info(LogUtil.info(LogUtil.GATE_OTHER,"cronTask", DateTimeUtil.getCurrentDateTimeStr()));
    }
    // 每 10 秒執行一次
    //@Scheduled(cron = "0/10 * * * * ?")
    public void executeTaskWithCronMulti() {
        log.info(LogUtil.info(LogUtil.GATE_OTHER,"cronTaskMulti", DateTimeUtil.getCurrentDateTimeStr()));
    }
}
