package com.snm.snauth.repository;

import com.snm.snauth.model.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Nullable
    User findBySocialMediaId(@NotNull String socialMediaId);
}
