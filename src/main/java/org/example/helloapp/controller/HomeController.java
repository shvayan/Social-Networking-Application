package org.example.helloapp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/post")
    public String post() {
        return "postList";
    }

    @GetMapping("chat")
    public String chat() {
        return "chat";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/verify")
    public String verify() { return "verify";}

    @GetMapping("/postView")
    public String postView() {
        return "postView";
    }

    @GetMapping("/guestProfile")
    public String guestProfile() {
        return "guest";
    }

    @GetMapping("/")
    public String index() { return "postList"; }
}
