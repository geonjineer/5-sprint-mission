package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.util.*;

public class JCFChannelRepository implements ChannelRepository {

    private final Map<UUID, Channel> channelHashMap = new HashMap<>();

    @Override
    public Channel create(Channel channel) {
        channelHashMap.put(channel.getChannelId(), channel);
        return channel;
    }

    @Override
    public Optional<Channel> findById(UUID channelId) {
        return Optional.ofNullable(channelHashMap.get(channelId));
    }

    @Override
    public List<Channel> findAll() {
        return new ArrayList<>(channelHashMap.values());
    }

    @Override
    public Optional<Channel> update(UUID channelId, Channel updatedChannel) {
        if (!channelHashMap.containsKey(channelId)) {
            return Optional.empty();
        }
        channelHashMap.put(channelId, updatedChannel);
        return Optional.of(updatedChannel);
    }

    @Override
    public boolean delete(UUID channelId) {
        return channelHashMap.remove(channelId) != null;
    }
}
