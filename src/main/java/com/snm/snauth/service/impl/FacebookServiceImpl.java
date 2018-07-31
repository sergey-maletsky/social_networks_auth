package com.snm.snauth.service.impl;

import com.snm.snauth.controller.handler.FacebookRequestHandler;
import com.snm.snauth.dto.UserDto;
import com.snm.snauth.exception.NotUniqueException;
import com.snm.snauth.service.FacebookService;
import com.snm.snauth.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class FacebookServiceImpl implements FacebookService {

    private static final Logger log = LoggerFactory.getLogger(FacebookServiceImpl.class);

    @Value("${facebook.app.id}")
    private String appId;

    @Value("${facebook.app.secret}")
    private String appSecret;

    @Value("${facebook.namespace}")
    private String namespace;

    @Value("${app.url}")
    private String appUrl;

    @Autowired
    private UserService userService;

    @Override
    public UserDto register(HttpServletRequest request, HttpServletResponse response) throws Exception {

        FacebookProfile facebookProfile = getFacebookProfile(request, appUrl, appId, appSecret, "/facebook/reg");
        UserDto existingUser = userService.getUserBySocialMediaId(facebookProfile.getId());
        if (existingUser != null) {
            throw new NotUniqueException("Пользователь уже существует");
        }

        UserDto newUser = new UserDto();
        newUser.setSocialMediaId(facebookProfile.getId());
        newUser.setEmail(facebookProfile.getEmail());
        newUser.setFirstName(facebookProfile.getFirstName());
        newUser.setLastName(facebookProfile.getLastName());

        userService.save(newUser);

        return newUser;
    }

    @Override
    public UserDto login(HttpServletRequest request, HttpServletResponse response) throws Exception {

        FacebookProfile facebookProfile = getFacebookProfile(request, appUrl, appId, appSecret, "/facebook/login");
        UserDto existingUser = userService.getUserBySocialMediaId(facebookProfile.getId());
        if (existingUser == null) {
            throw new EntityNotFoundException("Пользователя не существует в системе");
        }

        return existingUser;
    }

    private FacebookProfile getFacebookProfile(HttpServletRequest request, String appUrl, String appId, String appSecret, String redirectFunction) throws IOException {

        FacebookRequestHandler facebookRequestHandler = new FacebookRequestHandler(request, appUrl, appId, appSecret);
        String accessToken = facebookRequestHandler.getFacebookAccessToken(redirectFunction);

        if (accessToken == null) {
            throw new IllegalArgumentException("Проблемы с доступом к Facebook API");
        }

        Facebook facebook = new FacebookTemplate(accessToken, namespace);
        FacebookProfile profile;
        try {
            profile = facebook.userOperations().getUserProfile();
            if (profile == null) {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new EntityNotFoundException("Данные пользователя недоступны через Facebook API");
        }

        return profile;
    }
}
