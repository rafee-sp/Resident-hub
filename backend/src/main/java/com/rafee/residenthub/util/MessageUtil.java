package com.rafee.residenthub.util;

import org.springframework.util.StringUtils;

public class MessageUtil {


    public static String processTemplate(String message, String residentName, String flatNo){

        message = message.trim();

        return message.replace("{name}", residentName != null ? residentName : "")
                .replace("{flat}", flatNo != null ? flatNo : "");

    }

    public static String formatPhoneNumber(String phoneNumber, String phoneRegion) {


        if(!StringUtils.hasText(phoneNumber)) throw new IllegalArgumentException("Phone number cannot be blank or null");

        phoneNumber = phoneNumber.replaceAll("\\D", "");

        if(phoneNumber.length() != 10) throw new IllegalArgumentException("Invalid phone number length : "+phoneNumber);

        String countryCode = phoneRegion.equalsIgnoreCase("IN") ? "91" : "1";

        return "+"+ countryCode + phoneNumber;

    }

}
