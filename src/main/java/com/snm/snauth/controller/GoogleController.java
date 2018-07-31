package com.snm.snauth.controller;

import com.snm.snauth.exception.NotUniqueException;
import com.snm.snauth.service.GoogleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/google")
public class GoogleController {

    private static final Logger log = LoggerFactory.getLogger(GoogleController.class);

    @Autowired
    private GoogleService googleService;

    @GetMapping("/reg")
    public ModelAndView googleRegister(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) throws Exception {

        mv.setViewName("user");
        try {
            mv.addObject("user", googleService.register(request, response));
        } catch (Exception ex) {
            mv.addObject("error", ex.getMessage());
        }
        return mv;
    }

    @GetMapping("/login")
    public ModelAndView googleLogin(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) throws Exception {

        mv.setViewName("user");
        try {
            mv.addObject("user", googleService.login(request, response));
            mv.addObject("isLogin", true);
        } catch (Exception ex) {
            mv.addObject("error", ex.getMessage());
        }
        return mv;
    }
}
