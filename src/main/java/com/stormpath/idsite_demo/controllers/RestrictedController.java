package com.stormpath.idsite_demo.controllers;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.idsite.AccountResult;
import com.stormpath.sdk.lang.Strings;
import com.stormpath.sdk.servlet.account.AccountResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RestrictedController {
    @Value("#{ @environment['stormpath.organization'] }")
    private String organization;

    @Value("#{ @environment['stormpath.show.organization.field'] }")
    private Boolean showOrganizationField;

    @Value("#{ @environment['stormpath.template.root'] }")
    private String templateRoot = "ro";

    @Autowired
    Application app;

    @RequestMapping("/restricted/user")
    public String idSiteCallback(HttpServletRequest req, Model model) {
        Account account = AccountResolver.INSTANCE.getAccount(req);
        if (account != null) {
            Boolean showCrossLink = (Strings.countOccurrencesOf(req.getRequestURL().toString(), ".") > 1) ? false : true;
            model.addAttribute("showCrossLink", showCrossLink);

            return "restricted/user";
        }

        return "redirect:/login";
    }
}
