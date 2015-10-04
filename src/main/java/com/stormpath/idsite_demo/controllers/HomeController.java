package com.stormpath.idsite_demo.controllers;

import com.stormpath.sdk.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    Application app;

    @RequestMapping("/")
    public String home() {
        return "index";
    }

    @RequestMapping("/debug")
    public String debug(HttpServletRequest req, Model model) {
        model.addAttribute("request", req);
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> names = req.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            headers.put(name, req.getHeader(name));
        }
        model.addAttribute("headers", headers);
        return "debug";
    }
}
