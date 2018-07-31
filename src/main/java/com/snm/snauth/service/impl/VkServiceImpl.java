package com.snm.snauth.service.impl;

import com.snm.snauth.controller.handler.VkRequestHandler;
import com.snm.snauth.dto.UserDto;
import com.snm.snauth.exception.NotUniqueException;
import com.snm.snauth.service.UserService;
import com.snm.snauth.service.VkService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class VkServiceImpl implements VkService {

    private static final Logger log = LoggerFactory.getLogger(VkServiceImpl.class);

    @Value("${vk.app.id}")
    private String appId;

    @Value("${vk.app.secret}")
    private String appSecret;

    @Value("${app.url}")
    private String appUrl;

    @Autowired
    private UserService userService;

    @Override
    public UserDto register(HttpServletRequest request, HttpServletResponse response) throws Exception {

        VkRequestHandler vkRequestHandler = new VkRequestHandler(request, appUrl, appId, appSecret);
        JSONObject accessJson = vkRequestHandler.getVkAccessJson("/vk/reg");

        if (accessJson == null) {
            throw new IllegalArgumentException("Проблемы с доступом к VK API");
        }

        JSONObject vkUserData = vkRequestHandler.getVkUserData(accessJson.getString("access_token"), String.valueOf(accessJson.getInt("user_id")));

        if (vkUserData == null) {
            throw new EntityNotFoundException("Данные пользователя недоступны через VK API");
        }

        UserDto existingUser = userService.getUserBySocialMediaId(String.valueOf(vkUserData.getInt("id")));
        if (existingUser != null) {
            throw new NotUniqueException("Пользователь уже существует");
        }

        UserDto newUser = new UserDto();
        newUser.setSocialMediaId(String.valueOf(vkUserData.getInt("id")));
        newUser.setEmail(accessJson.getString("email") != null ? accessJson.getString("email") : "");
        newUser.setFirstName(vkUserData.getString("first_name") != null ? vkUserData.getString("first_name") : "");
        newUser.setLastName(vkUserData.getString("last_name") != null ? vkUserData.getString("last_name") : "");

        userService.save(newUser);
        return newUser;
    }

    @Override
    public UserDto login(HttpServletRequest request, HttpServletResponse response) throws Exception {

        VkRequestHandler vkRequestHandler = new VkRequestHandler(request, appUrl, appId, appSecret);
        JSONObject accessJson = vkRequestHandler.getVkAccessJson("/vk/login");

        if (accessJson == null) {
            throw new IllegalArgumentException("Проблемы с доступом к VK API");
        }

        UserDto existingUser = userService.getUserBySocialMediaId(String.valueOf(accessJson.getInt("user_id")));
        if (existingUser == null) {
            throw new EntityNotFoundException("Пользователя не существует в системе");
        }

        return existingUser;
    }
}
