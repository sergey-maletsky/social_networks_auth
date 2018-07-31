package com.snm.snauth.controller;

import com.snm.snauth.service.VkService;
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
@RequestMapping("/vk")
public class VkController {

    private static final Logger log = LoggerFactory.getLogger(VkController.class);

    @Autowired
    private VkService vkService;

    @GetMapping("/reg")
    public ModelAndView vkRegister(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) throws Exception {

        mv.setViewName("user");
        try {
            mv.addObject("user", vkService.register(request, response));
        } catch (Exception ex) {
            mv.addObject("error", ex.getMessage());
        }
        return mv;
    }

    @GetMapping("/login")
    public ModelAndView vkLogin(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) throws Exception {

        mv.setViewName("user");
        try {
            mv.addObject("user", vkService.login(request, response));
            mv.addObject("isLogin", true);
        } catch (Exception ex) {
            mv.addObject("error", ex.getMessage());
        }
        return mv;
    }
}
