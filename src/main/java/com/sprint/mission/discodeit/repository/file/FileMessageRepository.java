package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FileMessageRepository implements MessageRepository {

    private final Path directory = Paths.get(System.getProperty("user.dir"), "message_data");

    public FileMessageRepository() {
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e){
                throw new RuntimeException("message_data 폴더 생성 실패",e);
            }
        }
    }

    @Override
    public Message create(Message message) {
        Path filepath = directory.resolve(message.getChannelId() + ".ser");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath.toString()))) {
            oos.writeObject(message);
            return message;
        } catch (IOException e) {
            throw new RuntimeException("Message 파일 생성 실패", e);
        }
    }

    @Override
    public Optional<Message> findById(UUID messageId) {
        Path filePath = directory.resolve(messageId + ".ser");
        if (!Files.exists(filePath)) {
            return Optional.empty();
        } try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath.toString()))) {
            Message message = (Message) ois.readObject();
            return  Optional.of(message);
        } catch (IOException | ClassNotFoundException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Message> findAll() {
        List<Message> messages = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory, ".ser")) {
            for (Path path : stream) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path.toString()))) {
                    Message message = (Message) ois.readObject();
                    messages.add(message);
                } catch (Exception ignored) {
                }
            }
        } catch (IOException e){
            throw new RuntimeException("user_data 폴더 목록 조회 실패", e);
        }
        return messages;
    }

    @Override
    public Optional<Message> update(UUID messageId, Message updatedMessage) {
        Path filePath = directory.resolve(messageId + ".ser");
        if (!Files.exists(filePath)) {
            return Optional.empty();
        } try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath.toString()))) {
            oos.writeObject(updatedMessage);
            return Optional.of(updatedMessage);
        } catch (IOException e){
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(UUID messageId) {
        Path filePath = directory.resolve(messageId + ".ser");
        try {
            return Files.deleteIfExists(filePath);
        }catch (IOException e){
            return false;
        }
    }

}