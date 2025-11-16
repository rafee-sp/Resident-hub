package com.rafee.residenthub.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "building")
@Data
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "building_name")
    private String buildingName;

    @Column(name = "created_at", updatable = false, columnDefinition = "DATETIME")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "last_modified",  columnDefinition = "DATETIME")
    @UpdateTimestamp
    private LocalDateTime lastModified;
}
