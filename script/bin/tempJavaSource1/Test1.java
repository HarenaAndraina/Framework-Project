package com.controller;

import org.framework.annotation.Controller;
import org.framework.annotation.RequestMapping;

@Controller
public class Test1 {
    
    @RequestMapping("/hello")
    public String message(){
        return "blue";
    }
}
