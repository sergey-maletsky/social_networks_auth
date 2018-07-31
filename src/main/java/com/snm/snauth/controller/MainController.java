package com.snm.snauth.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/")
public class MainController {

    @ApiOperation("Показать главную страницу")
    @GetMapping
    public ModelAndView index(ModelAndView modelAndView) {

        modelAndView.setViewName("index");
        return modelAndView;
    }
}
