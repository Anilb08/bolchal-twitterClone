package com.ab.x.controller;

import com.ab.x.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Demo {

    @Autowired
    private User user;

    @GetMapping("/get-all")
    public void getAllData(){
        System.out.println("hey");
    }
}
