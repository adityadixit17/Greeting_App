package com.aditya.GreetingApp.Services;

import com.aditya.GreetingApp.Repository.GreetingRepository;
import com.aditya.GreetingApp.model.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
public class GreetingService {
    @Autowired
    GreetingRepository greetingRepository;
    public String getSimpleGreet(){
        return "Hello World";
    }
    public String getSimpleGreet(String firstName, String lastName){
        if(firstName != null && lastName != null) {
            return "Hello " + firstName + " " + lastName;
        } else if (firstName != null) {
            return "Hello " + firstName;
        }
        else if (lastName != null) {
            return "Hello " + lastName;
        }
        else {
            return "Hello World!";
        }
    }
    public Greeting saveGreeting(String firstName, String lastName) {
        String message;
        if (firstName != null && lastName != null) {
            message = "Hello, " + firstName + " " + lastName + "!";
        } else if (firstName != null) {
            message = "Hello, " + firstName + "!";
        } else if (lastName != null) {
            message = "Hello, " + lastName + "!";
        } else {
            message = "Hello World!";
        }

        Greeting greeting = new Greeting(message);
        return greetingRepository.save(greeting);
    }
    public Greeting getGreetById(Long id) {
        return greetingRepository.findById(id).orElseThrow(()->new RuntimeException("Greeting not found with id: " + id));
    }
    public List<Greeting> getAllGreetings() {
        return greetingRepository.findAll();
    }
}