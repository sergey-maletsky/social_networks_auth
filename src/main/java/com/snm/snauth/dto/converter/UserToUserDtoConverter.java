package com.snm.snauth.dto.converter;

import com.snm.snauth.dto.UserDto;
import com.snm.snauth.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDtoConverter implements Converter<User, UserDto> {

    @Override
    public UserDto convert(User user) {

        UserDto dto = new UserDto();
        dto.setSocialMediaId(user.getSocialMediaId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        return dto;
    }
}
