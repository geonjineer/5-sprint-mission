package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JavaApplication {

    // 폴더 내 모든 .ser 파일 삭제
    public static void clearDataFolder(String folderName) {
        Path dir = Paths.get(System.getProperty("user.dir"), folderName);
        if (Files.exists(dir)) {
            try { Files.list(dir).filter(p -> p.getFileName().toString().endsWith(".ser")).forEach(path -> {
                try { Files.delete(path);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            });
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        // 1. 데이터 폴더 정리
        clearDataFolder("user_data");
        clearDataFolder("channel_data");
        clearDataFolder("message_data");

        // 2. 서비스 준비
        ServiceFactory factory = ServiceFactory.getInstance();
        UserService userService = factory.getUserService();
        ChannelService channelService = factory.getChannelService();
        MessageService messageService = factory.getMessageService();

        // 3. CRUD 테스트 실행
        userCRUDTest(userService);
        channelCRUDTest(channelService);
        messageCRUDTest(messageService, userService, channelService);
    }

    // 사용자 CRUD 테스트
    public static void userCRUDTest(UserService userService) {
        System.out.println("--- 사용자(User) 서비스 테스트 시작 ---");
        User user1 = new User("Alice", "alice@example.com");
        User user2 = new User("Bob", "bob@example.com");
        User user3 = new User("Charlie", "charlie@example.com");

        userService.create(user1);
        userService.create(user2);
        userService.create(user3);
        System.out.println("\n[등록] 사용자 3명 등록 완료.");

        Optional<User> foundUser1 = userService.findById(user1.getUserId());
        foundUser1.ifPresent(user -> System.out.println("\n[조회] 단건 조회 (Alice): " + user));

        List<User> allUsers = userService.findAll();
        System.out.println("\n[조회] 모든 사용자 조회:");
        allUsers.forEach(System.out::println);

        User updatedUser = userService.updateUser(user1.getUserId(), "정건진", "atoo152@naver.com", "123123");
        System.out.println("\n[수정] Alice -> 정건진으로 수정 완료: " + updatedUser);

        System.out.println("[조회] 수정 후 정건진 재조회: " + userService.findById(user1.getUserId()).orElse(null));

        boolean deleted = userService.deleteById(user2.getUserId());
        System.out.println("\n[삭제] Bob 삭제 결과: " + (deleted ? "성공" : "실패"));

        Optional<User> foundUser2AfterDelete = userService.findById(user2.getUserId());
        System.out.println("[조회] Bob 삭제 후 재조회 (존재 여부): " + foundUser2AfterDelete.isPresent());
        List<User> remainingUsers = userService.findAll();
        System.out.println("남아있는 사용자:");
        remainingUsers.forEach(System.out::println);

        System.out.println("\n--- 사용자(User) 서비스 테스트 종료 ---\n");
    }

    // 채널 CRUD 테스트
    public static void channelCRUDTest(ChannelService channelService) {
        System.out.println("--- 채널(Channel) 서비스 테스트 시작 ---");
        Channel channel1 = new Channel("General", "일반적인 대화를 위한 채널");
        Channel channel2 = new Channel("Development", "개발 관련 논의 채널");

        channelService.create(channel1);
        channelService.create(channel2);
        System.out.println("\n[등록] 채널 2개 등록 완료.");

        Optional<Channel> foundChannel1 = channelService.findById(channel1.getChannelId());
        foundChannel1.ifPresent(channel -> System.out.println("\n[조회] 단건 조회 (General): " + channel));

        List<Channel> allChannels = channelService.findAll();
        System.out.println("\n[조회] 모든 채널 조회:");
        allChannels.forEach(System.out::println);

        Channel updatedChannel1 = channelService.update(channel1.getChannelId(), "General Chat", "자유로운 대화를 위한 채널");
        System.out.println("\n[수정] General -> General Chat으로 수정 완료: " + updatedChannel1);

        Optional<Channel> reFoundChannel1 = channelService.findById(channel1.getChannelId());
        reFoundChannel1.ifPresent(channel -> System.out.println("[조회] 수정 후 General Chat 재조회: " + channel));

        boolean deletedChannel = channelService.delete(channel2.getChannelId());
        System.out.println("\n[삭제] Development 채널 삭제 결과: " + (deletedChannel ? "성공" : "실패"));

        Optional<Channel> foundChannel2AfterDelete = channelService.findById(channel2.getChannelId());
        System.out.println("[조회] Development 채널 삭제 후 재조회 (존재 여부): " + foundChannel2AfterDelete.isPresent());
        List<Channel> remainingChannels = channelService.findAll();
        System.out.println("남아있는 채널:");
        remainingChannels.forEach(System.out::println);

        System.out.println("\n--- 채널(Channel) 서비스 테스트 종료 ---\n");
    }

    // 메시지 CRUD 테스트
    public static void messageCRUDTest(MessageService messageService, UserService userService, ChannelService channelService) {
        System.out.println("--- 메시지(Message) 서비스 테스트 시작 (심화 요구사항 포함) ---");

        // 테스트를 위해 유효한 사용자와 채널이 필요함
        List<User> userList = userService.findAll();
        List<Channel> channelList = channelService.findAll();
        if (userList.isEmpty() || channelList.isEmpty()) {
            System.out.println("메시지 테스트를 위한 사용자/채널이 부족합니다.");
            return;
        }
        User user1 = userList.get(0);
        Channel channel1 = channelList.get(0);

        Message msg1 = new Message("Hello everyone!", user1.getUserId(), channel1.getChannelId());
        Message msg2 = new Message("안녕하세요!", user1.getUserId(), channel1.getChannelId());
        Message msg3 = new Message("누구세요", user1.getUserId(), channel1.getChannelId());
        messageService.create(msg1);
        messageService.create(msg2);
        messageService.create(msg3);
        System.out.println("\n[등록] 유효한 메시지 등록 완료. : " + msg1);

        Message msgInvalidUser = new Message("This message should fail.", UUID.randomUUID(), channel1.getChannelId());
        System.out.println("\n[등록] 존재하지 않는 사용자 ID로 메시지 등록 시도 (실패 예상):");
        Message createdInvalidUserMsg = messageService.create(msgInvalidUser);
        System.out.println("생성 결과: " + (createdInvalidUserMsg != null ? "성공" : "실패 (예상대로)"));

        Message msgInvalidChannel = new Message("This message should also fail.", user1.getUserId(), UUID.randomUUID());
        System.out.println("\n[등록] 존재하지 않는 채널 ID로 메시지 등록 시도 (실패 예상):");
        Message createdInvalidChannelMsg = messageService.create(msgInvalidChannel);
        System.out.println("생성 결과: " + (createdInvalidChannelMsg != null ? "성공" : "실패 (예상대로)"));

        Optional<Message> foundMsg1 = messageService.findById(msg1.getMessageId());
        foundMsg1.ifPresent(message -> System.out.println("\n[조회] 단건 조회 (Hello everyone!): " + message));

        List<Message> allMessages = messageService.findAll();
        System.out.println("\n[조회] 모든 메시지 조회:");
        allMessages.forEach(System.out::println);

        Message updatedMsg1 = messageService.update(msg1.getMessageId(), "Hello, world! (updated)", null);
        System.out.println("\n[수정] 메시지 수정 완료: " + updatedMsg1);

        Optional<Message> reFoundMsg1 = messageService.findById(msg1.getMessageId());
        reFoundMsg1.ifPresent(message -> System.out.println("[조회] 수정 후 메시지 재조회: " + message));

        boolean deletedMsg = messageService.delete(msg1.getMessageId());
        System.out.println("\n[삭제] 메시지 삭제 결과: " + (deletedMsg ? "성공" : "실패"));

        Optional<Message> foundMsg1AfterDelete = messageService.findById(msg1.getMessageId());
        System.out.println("[조회] 메시지 삭제 후 재조회 (존재 여부): " + foundMsg1AfterDelete.isPresent());
        List<Message> remainingMessages = messageService.findAll();
        System.out.println("남아있는 메시지:");
        remainingMessages.forEach(System.out::println);

        System.out.println("\n--- 메시지(Message) 서비스 테스트 종료 ---");
    }
}
