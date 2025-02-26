package com.example.geo_api;


import com.example.geo_api.entity.JobStatus;
import com.example.geo_api.repository.JobStatusRepository;
import com.example.geo_api.service.ExportService;
import com.example.geo_api.service.ImportService;
import com.example.geo_api.service.JobStatusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FileProcessingTest {

    @Mock
    private ExportService exportService;

    @Mock
    private ImportService importService;

    @Mock
    private JobStatusService jobStatusService;

    @Mock
    private JobStatusRepository jobStatusRepository;

    @InjectMocks
    private FileProcessingTest fileProcessingTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testImportFile() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "test data".getBytes());
        JobStatus jobStatus = new JobStatus();
        jobStatus.setId(1L);
        jobStatus.setStatus("IN PROGRESS");

        when(jobStatusService.createJob()).thenReturn(jobStatus);
        doNothing().when(importService).importXLSFile(any(MultipartFile.class), any(Long.class));

        importService.importXLSFile(file, 1L);

        verify(importService, times(1)).importXLSFile(any(MultipartFile.class), any(Long.class));
    }

    @Test
    void testGetImportStatus() {
        JobStatus jobStatus = new JobStatus();
        jobStatus.setId(1L);
        jobStatus.setStatus("DONE");

        when(jobStatusService.getJobStatus(1L)).thenReturn(jobStatus);

        JobStatus status = jobStatusService.getJobStatus(1L);

        verify(jobStatusService, times(1)).getJobStatus(1L);
    }

    @Test
    void testExportToXLS() {
        JobStatus jobStatus = new JobStatus();
        jobStatus.setId(1L);
        jobStatus.setStatus("IN PROGRESS");

        when(jobStatusService.createJob()).thenReturn(jobStatus);
        doNothing().when(exportService).exportToXLS(any(Long.class));

        exportService.exportToXLS(1L);

        verify(exportService, times(1)).exportToXLS(any(Long.class));
    }

    @Test
    void testGetExportStatus() {
        JobStatus jobStatus = new JobStatus();
        jobStatus.setId(1L);
        jobStatus.setStatus("DONE");

        when(jobStatusService.getJobStatus(1L)).thenReturn(jobStatus);

        JobStatus status = jobStatusService.getJobStatus(1L);

        verify(jobStatusService, times(1)).getJobStatus(1L);
    }

    @Test
    void testGetExportedFile() throws IOException {
        JobStatus jobStatus = new JobStatus();
        jobStatus.setId(1L);
        jobStatus.setStatus("DONE");
        byte[] fileContent = "test data".getBytes();

        when(jobStatusService.getJobStatus(1L)).thenReturn(jobStatus);
        when(exportService.getExportedFile(1L)).thenReturn(fileContent);

        byte[] content = exportService.getExportedFile(1L);

        verify(exportService, times(1)).getExportedFile(1L);
    }
}