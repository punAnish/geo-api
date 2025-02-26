package com.example.geo_api.controller;

import com.example.geo_api.entity.JobStatus;
import com.example.geo_api.service.ExportService;
import com.example.geo_api.service.JobStatusService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/export")
public class ExportController {


    private final ExportService exportService;
    private final JobStatusService jobStatusService;


    public ExportController(ExportService exportService, JobStatusService jobStatusService) {
        this.exportService = exportService;
        this.jobStatusService = jobStatusService;
    }

    // API to export data to XLS file
    @GetMapping
    public ResponseEntity<Long> exportToXLS() {
        JobStatus jobStatus = jobStatusService.createJob();
        Long jobId = jobStatus.getId();
        exportService.exportToXLS(jobId);
        return ResponseEntity.ok(jobId);
    }

    // API to get export status
    @GetMapping("/{id}")
    public ResponseEntity<String> getExportStatus(@PathVariable Long id) {
        JobStatus jobStatus = jobStatusService.getJobStatus(id);
        return ResponseEntity.ok(jobStatus.getStatus());
    }

    // API to get exported file
    @GetMapping("/{id}/file")
    public ResponseEntity<byte[]> getExportedFile(@PathVariable Long id) throws IOException {
        JobStatus jobStatus = jobStatusService.getJobStatus(id);
        if (!"DONE".equals(jobStatus.getStatus())) {
            throw new IllegalStateException("Export is not completed yet.");
        }
        byte[] fileContent = exportService.getExportedFile(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=section_data.xlsx");
        headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        return ResponseEntity.ok().headers(headers).body(fileContent);
    }
}
