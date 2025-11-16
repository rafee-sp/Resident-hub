package com.rafee.residenthub.repository;

import com.rafee.residenthub.entity.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagsRepository extends JpaRepository<Tags, Integer> {

    @Query("SELECT t.name FROM Tags t WHERE t.id IN :ids")
    List<String> findTagsNamesById(@Param("ids") List<Integer> ids);

}
