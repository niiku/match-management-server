package com.match.management.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping(path = "hello")
    public Hello sayHello() {
        Hello hello = new Hello();
        hello.setGreeting("Hoi");
        hello.setName("Heinz");
        return hello;
    }

}

class Hello {
    private String greeting;
    private String name;

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
