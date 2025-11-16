package com.rafee.residenthub.entity;

import com.rafee.residenthub.dto.enums.CommunicationType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "communication_templates")
@Data
public class CommunicationTemplates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "template", nullable = false, length = 512)
    private String template;

    @Enumerated(EnumType.STRING)
    @Column(name = "communication_type")
    private CommunicationType communicationType;

}
