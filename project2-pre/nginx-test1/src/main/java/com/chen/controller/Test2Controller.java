package com.chen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Robert V
 * @create 2022-09-21-上午10:49
 */
@Controller
@RequestMapping("/vod")
public class Test2Controller {

    @GetMapping
    public String index() {
        return "index";
    }
}
