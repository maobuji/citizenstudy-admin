package com.citizen.study.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Administrator on 2019/3/27.
 */
@RestController
@RequestMapping("/access")
public class AccessTestController {

    @GetMapping("/test")
    public String accessTest() {
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat smft=new SimpleDateFormat("YYYY年MM月dd日  HH:mm:ss E");
        String nowString=smft.format(calendar.getTime());
        return "accessTest OK.Now time "+nowString;
    }
}
