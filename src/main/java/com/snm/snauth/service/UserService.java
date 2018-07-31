package com.snm.snauth.service;

import com.snm.snauth.dto.UserDto;
import org.jetbrains.annotations.NotNull;

public interface UserService {
    UserDto getUserBySocialMediaId(@NotNull String id);

    @NotNull UserDto save(@NotNull UserDto userDto);
}
