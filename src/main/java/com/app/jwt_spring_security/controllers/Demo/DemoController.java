package com.app.jwt_spring_security.controllers.Demo;

// Esta clase es una representación de la API que hayamos construído

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class DemoController {

    @PostMapping(value = "demo")
    public String welcome() {
        return "Welcome from secure endpoint";
    }

}
