package com.tracemeifyoucan.application.domain.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GovernorController {


    @RequestMapping(value = "/reserve", method = RequestMethod.GET)
    @ResponseBody()
    public int reserve() throws InterruptedException {
        Thread.sleep(1000);
        return 1;

    }
    @RequestMapping(value = "/release", method = RequestMethod.POST)
    public int release() throws InterruptedException {
        Thread.sleep(1000);
        return 1;
    }
}