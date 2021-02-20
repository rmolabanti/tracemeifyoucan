package com.tracemeifyoucan.publisher;

import com.google.cloud.opentelemetry.trace.TraceExporter;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.TracerProvider;
import io.opentelemetry.context.Scope;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class TracerUtil {

    public static Tracer getTracer() {
        TraceExporter traceExporter = null;
        try {
            //traceExporter = TraceExporter.createWithDefaultConfiguration();
            //OpenTelemetrySdk.getGlobalTracerManagement().addSpanProcessor(SimpleSpanProcessor.builder(traceExporter).build());
            TracerProvider tracerProvider = OpenTelemetry.getGlobalTracerProvider();
            return tracerProvider.get("publisher");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OpenTelemetry.getGlobalTracer("publisher");
    }

    public static <T> T withSpan(String name, Callable<T> consumer){
        Tracer tracer = getTracer();
        Span span = tracer.spanBuilder(name).startSpan();
        try (Scope scope = span.makeCurrent()) {
            return consumer.call();
        } catch (Throwable t) {
            span.setStatus(StatusCode.ERROR, "Change it to your error message");
        } finally {
            span.end(); // closing the scope does not end the span, this has to be done manually
        }
        return null;
    }
}
