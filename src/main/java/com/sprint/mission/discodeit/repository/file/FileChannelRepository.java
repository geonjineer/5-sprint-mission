package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FileChannelRepository implements ChannelRepository {

    private final Path directory = Paths.get(System.getProperty("user.dir"), "channel_date");

    public FileChannelRepository() {
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                throw new RuntimeException("chnnel_Data 폴더 생성 실패", e);
            }
        }
    }

    @Override
    public Channel create(Channel channel) {
        Path filePath = directory.resolve(channel.getChannelId() + ".ser");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath.toString()))) {
          oos.writeObject(channel);
          return channel;
        } catch (IOException e) {
            throw new RuntimeException("Channel 파일 생성 실패", e);
        }
    }

    @Override
    public Optional<Channel> findById(UUID channelId) {
        Path filePath = directory.resolve(channelId + ".ser");
        if (!Files.exists(filePath)) {
            return Optional.empty();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath.toString()))) {
            Channel channel = (Channel) ois.readObject();
            return Optional.of(channel);
        } catch (IOException | ClassNotFoundException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Channel> findAll() {
        List<Channel> channels = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory, "*.ser")) {
            for (Path path : stream) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path.toString()))) {
                    Channel channel = (Channel) ois.readObject();
                    channels.add(channel);
                } catch (Exception ignored) {}
            }
        } catch (IOException e) {
            throw new RuntimeException("channel_data 폴더 목록 조회 실패", e);
        }
        return channels;
    }

    @Override
    public Optional<Channel> update(UUID channelId, Channel updatedChannel) {
        Path filePath = directory.resolve(channelId + ".ser");

        if (!Files.exists(filePath)) {
            return Optional.empty();
        } try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath.toString()))) {
            oos.writeObject(updatedChannel);
            return Optional.of(updatedChannel);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(UUID channelId) {
        Path filePath = directory.resolve(channelId + ".ser");
        try {
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            return false;
        }
    }
}
