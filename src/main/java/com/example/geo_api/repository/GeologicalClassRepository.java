package com.example.geo_api.repository;

import com.example.geo_api.entity.GeologicalClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeologicalClassRepository extends JpaRepository<GeologicalClass, Long> {
}
