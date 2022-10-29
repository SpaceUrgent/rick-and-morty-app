package com.spaceurgent.rickandmortyapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping
    public String redirect() {
        return "redirect:/characters";
    }
}
