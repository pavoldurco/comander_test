package org.example.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

@Entity
@Table(name = "SUSERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @Column(nullable = false)
    @NotBlank(message = "User GUID is mandatory")
    private String userGuid;

    @Column(nullable = false)
    @NotBlank(message = "User name is mandatory")
    private String userName;

    public User() {
    }

    public User(int userId, String userGuid, String userName) {
        this.userId = userId;
        this.userGuid = userGuid;
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserGuid() {
        return userGuid;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && Objects.equals(userGuid, user.userGuid) && Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userGuid, userName);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userGuid='" + userGuid + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}