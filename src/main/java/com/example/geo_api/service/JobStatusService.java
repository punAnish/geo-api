package com.example.geo_api.service;

import com.example.geo_api.entity.JobStatus;
import com.example.geo_api.repository.JobStatusRepository;
import org.springframework.stereotype.Service;

@Service
public class JobStatusService {

    private final JobStatusRepository jobStatusRepository;

    public JobStatusService(JobStatusRepository jobStatusRepository) {
        this.jobStatusRepository = jobStatusRepository;
    }

    // create job
    public JobStatus createJob() {
        JobStatus jobStatus = new JobStatus();
        jobStatus.setStatus("In Progress");
        return jobStatusRepository.save(jobStatus);
    }

    // get job status by id
    public JobStatus getJobStatus(Long id) {
        return jobStatusRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Job with id " + id + " not found"));
    }
}
