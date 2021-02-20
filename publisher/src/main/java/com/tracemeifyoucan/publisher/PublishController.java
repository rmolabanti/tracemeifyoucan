package com.tracemeifyoucan.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.opentelemetry.trace.TraceExporter;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.TracerProvider;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class PublishController {

    @PostMapping("/publish")
    public String publish(@RequestBody PublishRequest publishRequest) throws JsonProcessingException {

        try {
            TraceExporter traceExporter = TraceExporter.createWithDefaultConfiguration();
            OpenTelemetrySdk.getGlobalTracerManagement().addSpanProcessor(SimpleSpanProcessor.builder(traceExporter).build());
            TracerProvider tracerProvider = OpenTelemetry.getGlobalTracerProvider();
            Tracer tracer = tracerProvider.get("publisher");
            Span publishSpan = tracer.spanBuilder("publish").startSpan();
            System.out.println(new ObjectMapper().writeValueAsString(publishRequest));
            try {
                Thread.sleep(1000);
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
