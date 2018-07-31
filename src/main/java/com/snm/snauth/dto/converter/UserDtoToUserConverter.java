package com.snm.snauth.dto.converter;

import com.snm.snauth.dto.UserDto;
import com.snm.snauth.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDtoToUserConverter implements Converter<UserDto, User> {

    @Override
    public User convert(UserDto dto) {

        User user = new User();
        user.setSocialMediaId(dto.getSocialMediaId());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        return user;
    }
}
