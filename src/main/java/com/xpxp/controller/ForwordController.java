package com.xpxp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForwordController {
    @GetMapping(value = {"/", "/{path:[^.]*}","/{path:^(?!api|static).*$}","/join/*"})
    public String index() {
        return "index";
    }
}