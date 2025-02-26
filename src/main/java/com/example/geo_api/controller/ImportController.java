package com.example.geo_api.controller;

import com.example.geo_api.entity.JobStatus;
import com.example.geo_api.service.ImportService;
import com.example.geo_api.service.JobStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/import")
public class ImportController {


    private final ImportService importService;
    private final JobStatusService jobStatusService;

    public ImportController(ImportService importService, JobStatusService jobStatusService) {
        this.importService = importService;
        this.jobStatusService = jobStatusService;
    }

    // API to handle XLS file upload
    @PostMapping
    public ResponseEntity<Long> importXLS(@RequestParam("file") MultipartFile file) throws IOException {
        JobStatus jobStatus = jobStatusService.createJob();
        Long jobId = jobStatus.getId();
        importService.importXLSFile(file, jobId);
        return ResponseEntity.ok(jobId);
    }

    // API to get import status
    @GetMapping("/{id}")
    public ResponseEntity<String> getImportStatus(@PathVariable Long id) {
        JobStatus jobStatus = jobStatusService.getJobStatus(id);
        return ResponseEntity.ok(jobStatus.getStatus());
    }
}
