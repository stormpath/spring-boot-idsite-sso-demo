package com.stormpath.idsite_demo.controllers;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.servlet.account.AccountResolver;
import com.stormpath.sdk.servlet.http.Resolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RestrictedController {

    @Value("#{ @environment['web.cross.link'] }")
    String crossLink;

    @Autowired
    Resolver<String> stormpathOrganizationNameKeyResolver;

    @RequestMapping("/restricted/user")
    public String restricted(HttpServletRequest req, Model model) {
        Account account = AccountResolver.INSTANCE.getAccount(req);

        if (account != null) {
            String org = getOrganizationNameKey(req);

            if (org != null && account.getApplications().getSize() > 1) {
                model.addAttribute("crossLink", "//" + org + "." + crossLink);
            }

            String templateSuffix = (org == null) ? "user" : org;

            return "restricted/" + templateSuffix;
        }

        return "redirect:/login";
    }

    private String getOrganizationNameKey(HttpServletRequest req) {
        return stormpathOrganizationNameKeyResolver.get(req, null);
    }
}
