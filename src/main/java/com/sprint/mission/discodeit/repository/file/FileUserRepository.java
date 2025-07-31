package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FileUserRepository implements UserRepository {

    private final Path directory = Paths.get(System.getProperty("user.dir"), "user_data");

    public FileUserRepository() {
        if (!Files.exists(directory)) {
            try {
                Files.createDirectory(directory);
            } catch (IOException e) {
                throw new RuntimeException("user_data 폴더 생성 실패", e);
            }
        }
    }

    @Override
    public User create(User user) {
        Path filePath = directory.resolve(user.getUserId() + ".ser");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath.toString()))) {
            oos.writeObject(user);
            return user;
        } catch (IOException e) {
            throw new RuntimeException("User 파일 생성 실패", e);
        }
    }

    @Override
    public Optional<User> findById(UUID userId) {
        Path filePath = directory.resolve(userId + ".ser");
        if (!Files.exists(filePath)) {
            return Optional.empty();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath.toString()))) {
            User user = (User) ois.readObject();
            return Optional.of(user);
        } catch (IOException | ClassNotFoundException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory, "*.ser")) {
            for (Path path : stream) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path.toString()))) {
                    User user = (User) ois.readObject();
                    users.add(user);
                } catch (Exception ignored) {}
            }
        } catch (IOException e) {
            throw new RuntimeException("user_data 폴더 목록 조회 실패", e);
        }
        return users;
    }

    @Override
    public Optional<User> updateId(UUID userId, User updateUser) {
        Path filePath = directory.resolve(userId + ".ser");
        if (!Files.exists(filePath)) {
            return Optional.empty();
        } try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath.toString()))) {
            oos.writeObject(updateUser);
            return Optional.of(updateUser);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteById(UUID userId) {
        Path filePath = directory.resolve(userId + ".ser");
        try {
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            return false;
        }
    }
}
