package com.aditya.GreetingApp.Controller;

import com.aditya.GreetingApp.CustomException.ResourceNotFoundException;
import com.aditya.GreetingApp.model.Greeting;
import com.aditya.GreetingApp.Repository.GreetingRepository;
import com.aditya.GreetingApp.Services.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/greeting")
public class GreetingController {
    @Autowired
    private GreetingRepository greetingRepo;
    @Autowired
    private GreetingService greetingService;
    @GetMapping
    public List<Greeting> getGreetings(){
        return greetingRepo.findAll();
    }
    @PostMapping
    public Greeting createGreeting(@RequestBody Greeting greet){
        return greetingRepo.save(greet);
    }
    @PutMapping("/id")
    public Greeting updateGreeting(@PathVariable Long ID, @RequestBody Greeting greet){;
        Greeting greeting=greetingRepo.findById(ID).orElseThrow(()-> new ResourceNotFoundException("Greeting not found with ID: "+ID));
        greeting.setMessage(greet.getMessage());
        return greetingRepo.save(greet);
    }
    @DeleteMapping("/{id}")
    public void deleteGreeting(@PathVariable Long ID){
        greetingRepo.deleteById(ID);
    }
    @Autowired
    private GreetingService simplegreet;
    @GetMapping("/simple")
    public String getSimpleGreet(){
        return simplegreet.getSimpleGreet();
    }
    @GetMapping("/message")
    public String getSimpleGreetWithName(@RequestParam(required=false) String firstname, @RequestParam(required = false) String lastname){
        return simplegreet.getSimpleGreet(firstname,lastname);
    }
    @PostMapping("/save")
    public Greeting saveGreeting(@RequestParam(required = false) String firstname, @RequestParam(required = false) String lastname){
        return greetingService.saveGreeting(firstname, lastname);
    }
    @GetMapping("getId/{id}")
    public Greeting getGreetingById(@PathVariable Long id){
        return greetingService.getGreetById(id);
    }
    @GetMapping("/getAll")
    public List<Greeting> getAllGreetings(){
        return greetingService.getAllGreetings();
    }
    @PutMapping("/updateRepo/{id}")
    public Greeting updateGreetingRepo(@PathVariable Long id, @RequestBody Greeting greetingDetails){
        return greetingService.updateGreeting(id, greetingDetails.getMessage());
    }
}