package com.rafee.residenthub.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "users",
uniqueConstraints = {
        @UniqueConstraint(name = "uk_users_username", columnNames = "user_name"),
        @UniqueConstraint(name = "uk_users_email", columnNames = "email")
}, indexes = {
        @Index(name = "idx_users_username_active", columnList = "user_name, deleted")
})
@Data
@ToString(exclude = {"password"})
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false, length = 45)
    private String username;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "role", nullable = false, length = 20)  // TODO - Role enhancement
    private String role;

    @Column(name = "email", nullable = false, length = 65)
    private String email;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime createdAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
    }

    @Override
    public String getUsername() {
        return username;
    }

}
