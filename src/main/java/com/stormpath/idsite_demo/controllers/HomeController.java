package com.stormpath.idsite_demo.controllers;

import com.stormpath.sdk.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @Autowired
    Application app;

    @Value("#{ @environment['stormpath.template.root'] }")
    private String templateRoot;


    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("appName", app.getName());
        model.addAttribute("appDescription", app.getDescription());

        return "index";
    }
}
