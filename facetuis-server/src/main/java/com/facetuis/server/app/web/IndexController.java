package com.facetuis.server.app.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class IndexController {


    @Value("{wechat.app.id}")
    private String name;

    @RequestMapping("/")
    public String index(){
        return "hello word !!!" ;
    }
}
