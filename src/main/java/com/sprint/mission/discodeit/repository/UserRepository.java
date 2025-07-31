package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    User create(User user);

    Optional<User> findById(UUID userId);

    List<User> findAll();

    Optional<User> updateId(UUID userId, User updateUser);

    boolean deleteById(UUID userId);

}
