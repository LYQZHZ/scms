package com.zerone.scms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/error")
public class ErrorController {
    private Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @RequestMapping(value = "denied", method = RequestMethod.GET)
    public String deniedForGet() {
        return "403";
    }

    @RequestMapping(value = "denied", method = RequestMethod.POST)
    public String deniedForPost() {
        return "403";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String errorForGet() {
        return "error";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String errorForPost() {
        return "error";
    }
}
