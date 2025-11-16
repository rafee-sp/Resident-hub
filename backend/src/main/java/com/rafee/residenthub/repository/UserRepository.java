package com.rafee.residenthub.repository;


import com.rafee.residenthub.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByUsername(String username);

    Page<User> findAllByOrderByUsernameAsc(Pageable pageable);

    Optional<User> findByEmail(String email);

}
