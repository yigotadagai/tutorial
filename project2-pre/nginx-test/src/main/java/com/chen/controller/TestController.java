package com.chen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Robert V
 * @create 2022-09-21-上午10:43
 */
@Controller
@RequestMapping("/edu/")
public class TestController {

    @RequestMapping
    public String index() {
        return "demo";
    }
}
