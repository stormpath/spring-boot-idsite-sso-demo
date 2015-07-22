package com.stormpath.idsite_demo.controllers;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.servlet.account.AccountResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RestrictedController {
    @Autowired
    Application app;

    @Value("#{ @environment['stormpath.sso.cross.link'] ?: '/' }")
    private String crossLink;

    @Value("#{ @environment['stormpath.sso.message.primary'] ?: 'Primary' }")
    private String messagePrimary;

    @Value("#{ @environment['stormpath.sso.message.secondary'] ?: 'Secondary' }")
    private String messageSecondary;


    @RequestMapping("/restricted/secret")
    public String secret(HttpServletRequest request, Model model) {
        Account account = AccountResolver.INSTANCE.getAccount(request);

        if (account == null) {
            return "redirect:/login";
        }

        model.addAttribute("appName", app.getName());
        model.addAttribute("crossLink", crossLink);
        model.addAttribute("messagePrimary", messagePrimary);
        model.addAttribute("messageSecondary", messageSecondary);
        return "restricted/secret";
    }
}
