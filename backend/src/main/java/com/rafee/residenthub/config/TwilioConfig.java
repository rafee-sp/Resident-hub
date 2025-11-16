package com.rafee.residenthub.config;

import com.twilio.Twilio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(prefix = "twilio")
@Data
@Validated
@Slf4j
public class TwilioConfig {

    @Autowired
    private final AppConfig appConfig;

    @NotBlank private String sid;
    @NotBlank private String authId;
    @NotBlank private String phone;
    @NotBlank private String phoneRegion;
    @NotNull  private double textRateLimit;
    @NotNull  private double callRateLimit;

    @EventListener(ApplicationReadyEvent.class)
    public void smsInit(){

        try{

            Twilio.init(sid, authId);
            log.info("Twilio initialized successfully");

        } catch (Exception e) {
            log.error("Failed to initialize Twilio: {}", e.getMessage(), e);
            throw new IllegalStateException("Twilio initialization failed", e);
        }
    }

    public String getTextMessageCallbackUrl(){
        return appConfig.getBackendUrl() + "/api/v1/callback/message";
    }

    public String getVoiceAnswerCallbackUrl(){
        return appConfig.getBackendUrl() + "/api/v1/callback/voice/answer";
    }

    public String getVoiceMessageCallbackUrl(){
        return appConfig.getBackendUrl() + "/api/v1/callback/voice";
    }


}
