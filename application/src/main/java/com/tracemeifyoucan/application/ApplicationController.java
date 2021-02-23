package com.tracemeifyoucan.application;

import com.amazonaws.xray.spring.aop.AbstractXRayInterceptor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.tracemeifyoucan.application.service.ApplicationService;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController extends AbstractXRayInterceptor {

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


    @Override
    @Pointcut("@within(com.amazonaws.xray.spring.aop.XRayEnabled) && bean(*Controller)")
    public void xrayEnabledClasses() {}
}
