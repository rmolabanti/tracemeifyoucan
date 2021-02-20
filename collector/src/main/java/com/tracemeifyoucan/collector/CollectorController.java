package com.tracemeifyoucan.collector;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.List;

@RestController
@EnableScheduling
public class CollectorController {

    @GetMapping("/collect")
    public Entity collect(){
        Entity entity = new Entity();
        entity.setName("name");
        return entity;
    }

    @Scheduled(fixedDelay = 1000)
    public void redispush(){
        Jedis jedis = new Jedis("localhost");
        jedis.lpush("Q1","TEST");
    }


    @Scheduled(fixedDelay = 1000)
    public  void redispop(){
        Jedis jedis = new Jedis("localhost");
        List<String> messages = jedis.blpop(0,"Q2");
        System.out.println(messages.get(1));
    }
}
