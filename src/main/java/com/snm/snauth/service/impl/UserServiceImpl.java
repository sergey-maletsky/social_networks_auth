package com.snm.snauth.service.impl;

import com.snm.snauth.dto.UserDto;
import com.snm.snauth.dto.converter.BaseConverter;
import com.snm.snauth.model.User;
import com.snm.snauth.repository.UserRepository;
import com.snm.snauth.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private BaseConverter converter;

    @Override
    public UserDto getUserBySocialMediaId(@NotNull String socialMediaId) {

        User user = repository.findBySocialMediaId(socialMediaId);
        return converter.convert(user, UserDto.class);
    }


    @NotNull
    @Override
    public UserDto save(@NotNull UserDto userDto) {

        User user = converter.convert(userDto, User.class);
        return converter.convert(repository.save(user), UserDto.class);
    }
}
