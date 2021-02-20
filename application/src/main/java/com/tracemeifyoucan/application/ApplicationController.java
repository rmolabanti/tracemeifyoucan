package com.tracemeifyoucan.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tracemeifyoucan.application.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {

    @Autowired
    ApplicationService service;


    @GetMapping("/simple")
    public String simple(){
        try {
            service.simpleRequestProcessor();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Simple Request completed";
    }
}
