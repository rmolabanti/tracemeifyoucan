package com.tracemeifyoucan.governor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;

@RestController
@EnableScheduling
public class GovernorController {

    @Autowired
    private TaskScheduler threadPoolTaskScheduler;
    private Jedis jedis;

    @PostConstruct
    public void setup(){
        jedis = new Jedis("localhost");
    }

    @RequestMapping(value = "/reserve", method = RequestMethod.GET)
    public int reserve() throws InterruptedException {
        Thread.sleep(1000);
        return 1;

    }
    @RequestMapping(value = "/release", method = RequestMethod.POST)
    public int release() throws InterruptedException {
        Thread.sleep(1000);
        return 1;
    }

    //@GetMapping("/redis")
    public void redis(){
        threadPoolTaskScheduler.schedule(() -> redispush(), new org.springframework.scheduling.support.PeriodicTrigger(5, TimeUnit.SECONDS));
    }

    //@Scheduled(fixedDelay = 5000)
    public  void redispush() {
        List<String> messages = jedis.blpop(0,"Q1");
        System.out.println("pop message from Q1 message: "+messages.get(1));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("push message to Q2 message: "+messages.get(1));
        jedis.rpush("Q2",messages.get(1));
    }



}