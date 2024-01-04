package com.workintech.Ecommerce.service;

public interface EmailService {

    String sendEmail(String to, String subject, String body);
}
