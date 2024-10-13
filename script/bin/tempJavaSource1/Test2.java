package com.controller;

import org.framework.annotation.Controller;
import org.framework.annotation.RequestMapping;
import org.framework.view.ModelView;

@Controller
public class Test2 {

    @RequestMapping("/bonjour")
    public ModelView message1(){
        ModelView model=new ModelView("bonjour.jsp");
        int a=1;
        model.addObject("bonjour",a);
        return model;
    }
    
}
