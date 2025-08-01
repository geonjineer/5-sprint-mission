package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BasicUserService implements UserService {

    private final UserRepository userRepository;

    public  BasicUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        if (user == null || user.getUserId() == null) {
            throw new IllegalArgumentException("User 및 user ID는 null이 될 수 없습니다.");
        }
        return userRepository.create(user);
    }

    @Override
    public Optional<User> findById(UUID userId) {
        return userRepository.findById(userId);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(UUID userId, String newUsername, String newEmail, String newPassword) {
        if (userId == null) {
            return null;
        }
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return null;
        }
        User user = optionalUser.get();

        // User 객체의 update 메서드만 사용 (setXXX 쓰지 않음)
        user.updateUser(newUsername, newEmail, newPassword);

        // 저장소에 변경 사항 반영
        userRepository.create(user); // 또는 userRepository.update(user) 등 저장 방식에 맞게

        return user;
    }

    /*
    @Override
    public Optional<User> updateId(UUID userId, User updateUser) {
        return userRepository.updateId(userId, updateUser);
    }

     */

    @Override
    public boolean deleteById(UUID userId) {
        return userRepository.deleteById(userId);
    }
}
