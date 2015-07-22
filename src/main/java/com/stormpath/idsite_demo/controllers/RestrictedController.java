package com.stormpath.idsite_demo.controllers;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.idsite.AccountResult;
import com.stormpath.sdk.idsite.IdSiteUrlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class RestrictedController {
    @Autowired
    Application app;

    private static final String ID_SITE_CALLBACK = "/restricted/id_site_callback";

    private String getBaseURL(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        String uri = request.getRequestURI();
        return url.substring(0, url.length() - uri.length());
    }

    @RequestMapping("/restricted/secret")
    public void idSiteStep1(HttpServletRequest request, HttpServletResponse response) {
        IdSiteUrlBuilder idSiteBuilder = app.newIdSiteUrlBuilder();
        idSiteBuilder.setCallbackUri(getBaseURL(request) + ID_SITE_CALLBACK);

        response.setStatus(HttpServletResponse.SC_FOUND);
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
        response.setHeader("Location", idSiteBuilder.build());
    }

    @RequestMapping(ID_SITE_CALLBACK)
    public String idSiteStep2(HttpServletRequest request, Model model) {
        AccountResult accountResult = app.newIdSiteCallbackHandler(request).getAccountResult();
        Account account = accountResult.getAccount();

        model.addAttribute("firstName", account.getGivenName());

        return "restricted/secret";
    }

    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        IdSiteUrlBuilder idSiteBuilder = app.newIdSiteUrlBuilder();
        idSiteBuilder.setCallbackUri(getBaseURL(request) + "/");
        idSiteBuilder.forLogout();

        response.setStatus(HttpServletResponse.SC_FOUND);
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
        response.setHeader("Location", idSiteBuilder.build());
    }
}
