package com.tracemeifyoucan.collector;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CollectorController {

    @GetMapping("/collect")
    public Entity collect(){
        Entity entity = new Entity();
        entity.setName("name");
        return entity;
    }
}
