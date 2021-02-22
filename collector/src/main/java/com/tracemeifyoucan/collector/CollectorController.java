package com.tracemeifyoucan.collector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RestController
@EnableScheduling
public class CollectorController {

    @Autowired
    CollectorService service;

    @Autowired
    private TaskScheduler threadPoolTaskScheduler;

    private Jedis jedis;
    @PostConstruct
    public void setup(){
        jedis = new Jedis("localhost");
    }

    @GetMapping("/collect")
    public Entity collect(){
        Entity entity = new Entity();
        entity.setName("name");
        return entity;
    }

    @GetMapping("/callgoapi/{time}")
    public String callgoApi(@PathVariable int time){
        return service.callGoApi(time);
    }

    //@GetMapping("/redis")
    public void redis(){
        threadPoolTaskScheduler.schedule(() -> redispush(), new org.springframework.scheduling.support.PeriodicTrigger(5, TimeUnit.SECONDS));
        threadPoolTaskScheduler.schedule(() -> redispop(), new org.springframework.scheduling.support.PeriodicTrigger(5, TimeUnit.SECONDS));
    }

    int count = 0;

    //@Scheduled(fixedDelay = 5000)
    public void redispush(){
        String msg = "msg"+count++;
        System.out.println("pushing to Q1 message: "+msg);
        jedis.lpush("Q1",msg);
    }


    //@Scheduled(fixedDelay = 5000)
    public  void redispop(){
        List<String> messages = jedis.blpop(0,"Q2");
        System.out.println("pop form Q2 message: "+messages.get(1));
    }

}
