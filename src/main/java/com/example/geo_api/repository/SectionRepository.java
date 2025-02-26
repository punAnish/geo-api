package com.example.geo_api.repository;

import com.example.geo_api.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SectionRepository extends JpaRepository<Section, Long> {

    boolean existsByName(String name);
    Section findByName(String name);

    // custom query to find section by geological class code
    List<Section> findByGeologicalClassesCode(@Param("code") String code);


}
