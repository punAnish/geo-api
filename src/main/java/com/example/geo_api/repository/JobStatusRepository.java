package com.example.geo_api.repository;

import com.example.geo_api.entity.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobStatusRepository extends JpaRepository<JobStatus, Long> {
}
