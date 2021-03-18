package com.mediary.Controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@CrossOrigin
@RestController
@RequestMapping("/api")
public class HelloWorldController {

    @GetMapping
    public String getHelloWorld(){
        String text = "Hello World!";
        return text;
    }
}
