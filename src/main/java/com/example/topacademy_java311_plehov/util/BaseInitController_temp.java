package com.example.topacademy_java311_plehov.util;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/s")
@RequiredArgsConstructor
public class BaseInitController_temp {


    @GetMapping("/init")
    public void init(){


    }


}
