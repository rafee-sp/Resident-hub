package com.rafee.residenthub.repository;

import com.rafee.residenthub.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessagesRepository extends JpaRepository<Messages, Long> {

    Optional<Messages> findBySid(String messageSID);
}
