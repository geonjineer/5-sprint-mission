package com.sprint.mission.discodeit.entity;

import java.time.Instant;
import java.util.UUID; // UUID를 사용하기 위한 import
import java.io.Serializable; // 직렬화 기능 sprint-2

//유저 도메인 모델 클래스
//유저의 ID, 생성 시간, 수정 시간, 이름, 이메일 정보를 관리한다.
// implements Serializable 직렬화 상속 sprint-2
public class User implements Serializable {
    private UUID userId; //객체를 식별하기 위한 고유 id (UUID type)
    private Long createdAt; // 객체 생성 시간 저장 (Unix time stamp, Long type)
    private Long updatedAt; // 객체 수정 시간 (Unix time stamp, Long type)
    private String userName; // 유저 이름
    private String email; // 유저 이메일
    private String password; // 멘토님 수정사항

    // User 객체의 생성자
    // id와 createdAt는 생성 시점에 초기화 되고, 다른 필드들은 파라미터를 통해 초기화된다.
    // @param userName 유저 이름
    // @param email 유저 이메일
    public User() {
    }

    public User(String userName, String email) {
        this.userId = UUID.randomUUID(); // 새로운 UUID를 생성하여 id 초기화
        // this.createdAt = System.currentTimeMillis(); // 현재 시간을 유닉스 타임스탬프로 createdAt 초기화
        this.createdAt = Instant.now().getEpochSecond(); // 멘토님이 알려주신 초기화 방법
        this.updatedAt = this.createdAt; // 초기 updatedAt은 createdAt과 동일하게 설정한다.this.userName = userName;
        this.userName = userName;
        this.email = email;
        this.password = UUID.randomUUID().toString(); // 멘토님 수정사항
    }

    //User 객체 Getter -> 미션에 Setter가 없으므로 Getter만 생성.
    public UUID getUserId() {
        return userId;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


    //User 객체의 필드를 수정하는 메서드
    // 수정된 필드에 따라 updatedAt를 현재 시간으로 업데이트한다.
    // @param userName 새로운 유저 이름 (null이 아니면 업데이트)
    // @param email 새로운 유저 이메일 (null이 아니면 업데이트)
    /* public void updateUser(String userName, String email) {
        boolean changed = false; // 변경 여부를 추적하는 플래그

        if (userName != null && !userName.equals(this.userName)) {
            this.userName = userName;
            changed = true; //이름 변경안됨
        }
        if (email != null && !email.equals(this.email)) {
            this.email = email;
            changed = true; // 이메일 변경 안됨
        }

        //필드가 변경되었을 경우에만 updatedAt를 업데이트
        if (changed) {
            this.updatedAt = Instant.now().getEpochSecond();
        }
    }
    */

    //멘토님이 알려주신 업데이트 방법
    public void updateUser(String newUsername, String newEmail, String newPassword) {
        boolean anyValueUpdated = false;
        if (newUsername != null && !newUsername.equals(this.userName)) {
            this.userName = newUsername;
            anyValueUpdated = true;
        }
        if (newEmail != null && !newEmail.equals(this.email)) {
            this.email = newEmail;
            anyValueUpdated = true;
        }
        if (newPassword != null && !newPassword.equals(this.password)) {
            this.password = newPassword;
            anyValueUpdated = true;
        }
        if (anyValueUpdated) {
            this.updatedAt = Instant.now().getEpochSecond();
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}

