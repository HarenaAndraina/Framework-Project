package com.controller;

import org.framework.annotation.Controller;
import org.framework.annotation.RequestMapping;
import org.framework.annotation.RestAPI;

@Controller
public class Test3 {
    
    @RestAPI("/json")
    public String message(){
        return "jsonjsonnn";
    }
}
