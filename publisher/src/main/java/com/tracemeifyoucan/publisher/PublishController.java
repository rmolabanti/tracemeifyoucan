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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
public class PublishController {
    public static final Logger logger = LoggerFactory.getLogger(PublishController.class);

    @PostMapping("/publish")
    public String publish(@RequestBody PublishRequest publishRequest) throws JsonProcessingException {

        try {
            TraceConfiguration configuration = TraceConfiguration.builder()
                //.setCredentials(new GoogleCredentials(new AccessToken(accessToken, expirationTime)))
                .setProjectId("rep-sandbox").build();

            //TraceExporter traceExporter = TraceExporter.createWithConfiguration(configuration);
            TraceExporter traceExporter = TraceExporter.createWithDefaultConfiguration();
            OpenTelemetrySdk.getGlobalTracerManagement().addSpanProcessor(SimpleSpanProcessor.builder(traceExporter).build());
            TracerProvider tracerProvider = OpenTelemetry.getGlobalTracerProvider();
            Tracer tracer = tracerProvider.get("publisher");
            Span publishSpan = tracer.spanBuilder("publish").startSpan();
            logger.info(new ObjectMapper().writeValueAsString(publishRequest));
            try {
                logger.warn("waiting 1000");
                Thread.sleep(1000);
                RestTemplate restTemplate = new RestTemplate();
                Integer count = restTemplate.getForObject("http://localhost:8081/reserve",Integer.class);
                logger.info("Reserved 1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishSpan.end();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Published "+publishRequest.getId();
    }
}
