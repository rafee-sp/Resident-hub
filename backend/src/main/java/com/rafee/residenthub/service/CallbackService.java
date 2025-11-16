package com.rafee.residenthub.service;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface CallbackService {

    boolean isValidTwilioResponse(Map<String, String> paramsMap, String signature, HttpServletRequest request);

    void handleTextMessageCallback(Map<String, String> paramsMap);

    String generateVoiceAnswer(String token);

    void handleVoiceMessageCallback(Map<String, String> paramsMap);
}
