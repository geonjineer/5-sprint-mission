package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.util.*;

public class JCFUserRepository implements UserRepository {

    private final Map<UUID, User> userHashMap = new HashMap<>();

    @Override
    public User create(User user) {
        userHashMap.put(user.getUserId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(UUID userId) {
        return Optional.ofNullable(userHashMap.get(userId));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userHashMap.values());
    }

    @Override
    public Optional<User> updateId(UUID userId, User updateUser) {
        if (!userHashMap.containsKey(userId)) {
            return Optional.empty();
        }
        userHashMap.put(userId, updateUser);
        return Optional.of(updateUser);
    }

    @Override
    public boolean deleteById(UUID userId) {
        return userHashMap.remove(userId) != null;
    }
}
