package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class loitrangcontroller {

    @GetMapping("/loitrang")
    public String index() {
        return "thongbaoloi/loitrang";
    }
}
