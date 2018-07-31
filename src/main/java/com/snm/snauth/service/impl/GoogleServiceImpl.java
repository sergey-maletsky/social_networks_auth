package com.snm.snauth.service.impl;

import com.snm.snauth.controller.handler.GoogleRequestHandler;
import com.snm.snauth.dto.UserDto;
import com.snm.snauth.exception.NotUniqueException;
import com.snm.snauth.service.GoogleService;
import com.snm.snauth.service.UserService;
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
public class GoogleServiceImpl implements GoogleService {

    private static final Logger log = LoggerFactory.getLogger(GoogleServiceImpl.class);

    @Value("${google.client.id}")
    private String appId;

    @Value("${google.client.secret}")
    private String appSecret;

    @Value("${app.url}")
    private String appUrl;

    @Autowired
    private UserService userService;

    @Override
    public UserDto register(HttpServletRequest request, HttpServletResponse response) throws Exception {

        JSONObject googleUserData = getGoogleUserData(request, appUrl, appId, appSecret, "/google/reg");

        UserDto existingUser = userService.getUserBySocialMediaId(googleUserData.getString("id"));
        if (existingUser != null) {
            throw new NotUniqueException("Пользователь уже существует");
        }

        UserDto newUser = new UserDto();
        newUser.setSocialMediaId(googleUserData.getString("id"));
        newUser.setEmail(googleUserData.getString("email"));
        newUser.setFirstName(googleUserData.getString("given_name"));
        newUser.setLastName(googleUserData.getString("family_name"));

        userService.save(newUser);

        return newUser;
    }

    @Override
    public UserDto login(HttpServletRequest request, HttpServletResponse response) throws Exception {

        JSONObject googleUserData = getGoogleUserData(request, appUrl, appId, appSecret, "/google/login");

        UserDto existingUser = userService.getUserBySocialMediaId(googleUserData.getString("id"));
        if (existingUser == null) {
            throw new EntityNotFoundException("Пользователя не существует в системе");
        }

        return existingUser;
    }

    private JSONObject getGoogleUserData(HttpServletRequest request, String appUrl, String appId, String appSecret, String redirectFunction) throws Exception {
        GoogleRequestHandler googleRequestHandler = new GoogleRequestHandler(request, appUrl, appId, appSecret);
        String accessToken = googleRequestHandler.getGoogleAccessToken(redirectFunction);

        if (accessToken == null) {
            throw new IllegalArgumentException("Проблемы с доступом к Google API");
        }

        JSONObject googleUserData = googleRequestHandler.getGoogleUserData(accessToken);

        if (googleUserData == null) {
            throw new EntityNotFoundException("Данные пользователя недоступны через Google API");
        }

        return googleUserData;
    }
}
