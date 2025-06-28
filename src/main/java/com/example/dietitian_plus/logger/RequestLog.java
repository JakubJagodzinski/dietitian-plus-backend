package com.example.dietitian_plus.logger;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "request_logs")
public class RequestLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId;

    private LocalDateTime timestamp;

    private String method;

    private String uri;

    @Column(name = "query_string")
    private String queryString;

    private String status;

    @Column(name = "client_ip")
    private String clientIp;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "duration_ms")
    private Long durationMs;

    @Column(name = "trace_id")
    private String traceId;

    private String type;

}
