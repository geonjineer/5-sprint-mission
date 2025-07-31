package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BasicMessageService implements MessageService {

    private final MessageRepository messageRepository;

    public BasicMessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message create(Message message) {
        if (message == null) {
            throw new IllegalArgumentException("Message는 null이 될 수 없습니다.");
        }
        return messageRepository.create(message);
    }

    @Override
    public Optional<Message> findById(UUID messageId) {
        return messageRepository.findById(messageId);
    }

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public Optional<Message> update(UUID messageId, Message updatedMessage) {
        return messageRepository.update(messageId, updatedMessage);
    }

    @Override
    public boolean delete(UUID messageId) {
        return messageRepository.delete(messageId);
    }
}
