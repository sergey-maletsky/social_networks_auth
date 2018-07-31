package com.snm.snauth.service;

import com.snm.snauth.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface FacebookService {

    UserDto register(HttpServletRequest request, HttpServletResponse response) throws Exception;

    UserDto login(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
