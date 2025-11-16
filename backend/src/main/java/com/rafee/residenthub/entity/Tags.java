package com.rafee.residenthub.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tags")
@Data
public class Tags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

}
