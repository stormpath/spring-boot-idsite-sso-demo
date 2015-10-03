package com.stormpath.idsite_demo.controllers;

import com.stormpath.sdk.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @Autowired
    Application app;

    @RequestMapping("/")
    public String home() {
        return "index";
    }
}
