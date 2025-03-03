package com.aditya.GreetingApp.Services;

import org.springframework.stereotype.Service;

@Service
public class GreetingService {
    public String getSimpleGreeting() {
        return "Hello World!";
    }
    public String getSimpleGreeting(String firstname, String lastname) {
        return "Hello " + firstname + " " + lastname +"!";
    }
}