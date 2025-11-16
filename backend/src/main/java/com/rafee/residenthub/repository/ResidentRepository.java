package com.rafee.residenthub.repository;

import com.rafee.residenthub.entity.Resident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ResidentRepository extends JpaRepository<Resident, Long>, JpaSpecificationExecutor<Resident> {

    Optional<Resident> findByPhoneNumber(String phoneNumber);

    Page<Resident> findAllByOrderByIdDesc (Pageable page);

    Page<Resident> findById(long id, Pageable pageable);

    Page<Resident> findByResidentNameContainingIgnoreCase(String filterValue, Pageable pageable);

    Page<Resident> findByPhoneNumberContainingIgnoreCase(String filterValue, Pageable pageable);

    Page<Resident> findByBuilding(String filterValue, Pageable pageable);

    Optional<Resident> findByFlatNo(String flatNo);

    @Query("SELECT COUNT(r) FROM Resident r")
    Long getTotalResidentsCount();

}
