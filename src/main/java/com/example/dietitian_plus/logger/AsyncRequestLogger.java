package com.example.dietitian_plus.logger;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AsyncRequestLogger {

    private final RequestLogRepository requestLogRepository;

    public AsyncRequestLogger(RequestLogRepository requestLogRepository) {
        this.requestLogRepository = requestLogRepository;
    }

    @Async
    public void logRequest(String method,
                           String uri,
                           String queryString,
                           String clientIp,
                           String userAgent,
                           String status,
                           UUID userId,
                           Long durationMs,
                           String traceId,
                           LocalDateTime timestamp,
                           String type) {

        RequestLog log = new RequestLog();

        log.setMethod(method);
        log.setUri(uri);
        log.setQueryString(queryString);
        log.setClientIp(clientIp);
        log.setUserAgent(userAgent);
        log.setStatus(status);
        log.setUserId(userId);
        log.setDurationMs(durationMs);
        log.setTraceId(traceId);
        log.setTimestamp(timestamp);
        log.setType(type);

        requestLogRepository.save(log);
    }

}
