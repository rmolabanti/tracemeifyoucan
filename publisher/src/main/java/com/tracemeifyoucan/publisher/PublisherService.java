package com.tracemeifyoucan.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import io.opentelemetry.extension.auto.annotations.WithSpan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PublisherService {
    public static final Logger logger = LoggerFactory.getLogger(PublisherService.class);

    @WithSpan
    public Entity publish(Entity entity) {
        try {
            logger.info(new ObjectMapper().writeValueAsString(entity));
            logger.warn("Waiting 1000");
            Thread.sleep(1000);
            RestTemplate restTemplate = new RestTemplate();
            Integer count = restTemplate.getForObject("http://localhost:8083/reserve",Integer.class);
            logger.info("Reserved 1");
        } catch (InterruptedException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return entity;
    }

    public Entity publishWithAttribute(Entity entity) {
        Tracer tracer = TracerUtil.getTracer();
        Span span = tracer.spanBuilder("publishWithAttribute").startSpan();
        span.setAttribute("source","bing");
        try (Scope scope = span.makeCurrent()) {
            span.setAttribute("operation","update");
            logger.info(new ObjectMapper().writeValueAsString(entity));
            RestTemplate restTemplate = new RestTemplate();
            Integer count = restTemplate.getForObject("http://localhost:8083/reserve",Integer.class);
            logger.info("Reserved 1");
        } catch (Throwable t) {
            span.setStatus(StatusCode.ERROR, "Change it to your error message");
        } finally {
            span.end(); // closing the scope does not end the span, this has to be done manually
        }
        return entity;
    }
}
