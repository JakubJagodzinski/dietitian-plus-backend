package com.example.dietitian_plus.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "stripe")
public class StripeConfig {

    private String apiKey;

    private String webhookSecret;

    private String priceId;

    @PostConstruct
    public void init() {
        Stripe.apiKey = apiKey;
    }

}
