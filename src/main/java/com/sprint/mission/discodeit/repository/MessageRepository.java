package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository {

    Message create(Message message);

    Optional<Message> findById(UUID messageId);

    List<Message> findAll();

    Optional<Message> update(UUID messageId, Message updatedMessage);

    boolean delete(UUID messageId);
}
