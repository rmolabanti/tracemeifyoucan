package com.tracemeifyoucan.governor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import redis.clients.jedis.Jedis;


@RestController
@EnableScheduling
public class GovernorController {


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

    @Scheduled(fixedDelay = 1000)
    public  void redispush() throws InterruptedException {
        Jedis jedis = new Jedis("localhost");
        List<String> messages = jedis.blpop(0,"Q1");
        System.out.println(messages.get(1));
        System.out.println("in governer");
        Thread.sleep(1000);
        jedis.rpush("Q2",messages.get(1));
    }



}