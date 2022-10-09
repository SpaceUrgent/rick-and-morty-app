package com.spaceurgent.rickandmortyapp.controller;

import com.spaceurgent.rickandmortyapp.service.HttpClient;
import com.spaceurgent.rickandmortyapp.service.SynchronizationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {
    private final SynchronizationService service;

    public DemoController(SynchronizationService service) {
        this.service = service;
    }

    @GetMapping
    public String doDemo() {
        service.syncData();
        return "Done!";
    }
}
