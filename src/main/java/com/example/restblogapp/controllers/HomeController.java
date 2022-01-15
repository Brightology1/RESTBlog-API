package com.example.restblogapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value="/")
    public String index(){
        return "index";
    }

    @GetMapping(value="/registration")
    public String register(){
        return "register";
    }


    @GetMapping(value="post/{id}")
    public String singlePost(){
        return "post";
    }

}
