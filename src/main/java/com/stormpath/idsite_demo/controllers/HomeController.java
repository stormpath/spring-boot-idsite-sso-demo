package com.stormpath.idsite_demo.controllers;

import com.stormpath.sdk.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
    @Autowired
    Application app;

    @RequestMapping("/")
    public String home(HttpServletRequest req, Model model) {
        model.addAttribute("status", req.getParameter("status"));

        model.addAttribute("appName", app.getName());
        model.addAttribute("appDescription", app.getDescription());

        return "home";
    }
}
