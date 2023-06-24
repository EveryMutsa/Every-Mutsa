package com.example.everymutsa.web.member.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name", length = 32, nullable = false)
    private String name;

    @Column(name = "token", length = 128, nullable = false)
    private String token;

    @Column(name = "email", length = 32)
    private String email;

    @Column(name = "passwd", length = 128, nullable = false)
    private String passwd;

    @Column(name = "nick", length = 32, nullable = false)
    private String nick;

    @Column(name = "accessed_at", length = 32, nullable = false)
    private LocalDateTime role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class", nullable = false)
    private com.example.everymutsa.web.member.domain.entity.Class Class;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "accessed_at")
    private LocalDateTime accessedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
