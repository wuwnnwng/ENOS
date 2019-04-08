package com.wwn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ThymeleafController {

    @RequestMapping("/index3")
    public String index(ModelMap map){
        map.addAttribute("name","haozz");
        return "index";
    }
}
 
