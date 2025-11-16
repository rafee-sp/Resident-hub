package com.rafee.residenthub.repository;

import com.rafee.residenthub.dto.enums.CommunicationType;
import com.rafee.residenthub.entity.CommunicationTemplates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunicationTemplateRepository extends JpaRepository<CommunicationTemplates, Integer> {

    List<CommunicationTemplates> findAllByCommunicationType(CommunicationType  communicationType);
}
