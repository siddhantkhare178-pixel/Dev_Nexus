package com.devnexus.sdk;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Spring Boot Global Exception Handler for automated DevNexus telemetry.
 */
@ControllerAdvice
public class DevNexusGlobalExceptionHandler {

    private final DevNexusClient DevNexusClient;

    public DevNexusGlobalExceptionHandler(DevNexusClient DevNexusClient) {
        this.DevNexusClient = DevNexusClient;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleUnhandledException(Exception ex) {
        // Automatically capture and send to DevNexus platform
        Map<String, String> tags = new HashMap<>();
        tags.put("source", "Spring Boot ControllerAdvice");
        
        DevNexusClient.captureException(ex, "CRITICAL", tags);

        Map<String, Object> body = new HashMap<>();
        body.put("status", 500);
        body.put("error", "Internal Server Error");
        body.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
