package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.util.*;

public class JCFMessageRepository implements MessageRepository {

    private final Map<UUID, Message> messageHashMap = new HashMap<>();

    @Override
    public Message create(Message message) {
        messageHashMap.put(message.getMessageId(), message);
        return message;
    }

    @Override
    public Optional<Message> findById(UUID messageId) {
        return Optional.ofNullable(messageHashMap.get(messageId));
    }

    @Override
    public List<Message> findAll() {
        return new ArrayList<>(messageHashMap.values());
    }

    @Override
    public Optional<Message> update(UUID messageId, Message updatedMessage) {
        if (!messageHashMap.containsKey(messageId)) {
            return Optional.empty();
        }
        messageHashMap.put(messageId, updatedMessage);
        return Optional.of(updatedMessage);
    }

    @Override
    public boolean delete(UUID messageId) {
        return messageHashMap.remove(messageId) != null;
    }
}
