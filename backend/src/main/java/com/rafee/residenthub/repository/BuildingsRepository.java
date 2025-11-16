package com.rafee.residenthub.repository;


import com.rafee.residenthub.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BuildingsRepository extends JpaRepository<Building, Integer> {

    @Query("SELECT b.buildingName FROM Building b where b.id = :id")
    Optional<String> findBuildingNameById(@Param("id") Integer id);
}
