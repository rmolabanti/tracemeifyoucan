package com.tracemeifyoucan.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.opentelemetry.trace.TraceConfiguration;
import com.google.cloud.opentelemetry.trace.TraceExporter;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.TracerProvider;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;

@RestController
public class PublishController {
    public static final Logger logger = LoggerFactory.getLogger(PublishController.class);

    @Autowired
    private PublisherService service;

    @PostMapping("/publish/{time}")
    public Entity publish(@RequestBody Entity entity,@PathVariable int time) throws JsonProcessingException {
            return TracerUtil.withSpan("manual span in publish",() ->{
                logger.info(new ObjectMapper().writeValueAsString(entity));
                try {
                    logger.warn("Waiting "+time);
                    Thread.sleep(time);
                    RestTemplate restTemplate = new RestTemplate();
                    Integer count = restTemplate.getForObject("http://localhost:8083/reserve",Integer.class);
                    logger.info("Reserved 1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return entity;
            });

    }


    @PostMapping("/publish/annotation")
    public Entity publish2(@RequestBody Entity entity) throws JsonProcessingException {
        return TracerUtil.withSpan("publishWithAnnotation",() ->{
            return service.publish(entity);
        });
    }

    @PostMapping("/publish/attribute")
    public Entity publish3(@RequestBody Entity entity) throws JsonProcessingException {
            return service.publishWithAttribute(entity);
    }
}
