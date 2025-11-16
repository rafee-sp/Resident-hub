package com.rafee.residenthub.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "residents", indexes = {
        @Index(name = "idx_residents_phone", columnList = "phone_number"),
        @Index(name = "idx_residents_flat", columnList = "flat_no"),
})
@Data
public class Resident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "resident_name")
    private String residentName;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id")
    private Building building;

    @Column(name = "flat_no")
    private String flatNo;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_modified", columnDefinition = "DATETIME")
    private LocalDateTime lastModified;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "resident_tag", joinColumns = @JoinColumn(name = "resident_id"), inverseJoinColumns = @JoinColumn(name = "tags_id"))
    private Set<Tags> tags = new HashSet<>();

}
