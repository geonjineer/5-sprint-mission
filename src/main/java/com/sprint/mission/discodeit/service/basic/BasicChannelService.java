package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BasicChannelService implements ChannelService {

    private ChannelRepository channelRepository;

    public BasicChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Override
    public Channel create(Channel channel) {
        if (channel == null || channel.getChannelId() == null) {
            throw new IllegalArgumentException("Channel 및 채널 ID는 null이 될 수 없습니다.");
        }
        return channelRepository.create(channel);
    }

    @Override
    public Optional<Channel> findById(UUID channelId) {
        return channelRepository.findById(channelId);
    }

    @Override
    public List<Channel> findAll() {
        return channelRepository.findAll();
    }

    @Override
    public Optional<Channel> update(UUID channelId, Channel updatedChannel) {
        return channelRepository.update(channelId, updatedChannel);
    }

    @Override
    public boolean delete(UUID channelId) {
        return channelRepository.delete(channelId);
    }
}
