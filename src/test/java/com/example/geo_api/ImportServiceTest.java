package com.example.geo_api;

import com.example.geo_api.entity.JobStatus;
import com.example.geo_api.repository.JobStatusRepository;
import com.example.geo_api.service.GeologicalClassService;
import com.example.geo_api.service.ImportService;
import com.example.geo_api.service.SectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ImportServiceTest {

    @Mock
    private JobStatusRepository jobStatusRepository;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private ImportService importService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testImportXLSFile() throws IOException {
        JobStatus jobStatus = new JobStatus();
        jobStatus.setId(1L);
        jobStatus.setStatus("IN PROGRESS");

        when(jobStatusRepository.findById(1L)).thenReturn(Optional.of(jobStatus));

        // Provide a non-empty InputStream with valid XLSX data
        try (InputStream validXlsxData = getClass().getResourceAsStream("/section-data.xlsx")) {
            when(file.getInputStream()).thenReturn(validXlsxData);

            importService.importXLSFile(file, 1L);

            verify(jobStatusRepository, times(1)).save(any(JobStatus.class));
        }
    }

    @Test
    void testImportWithInvalidFileFormat() throws IOException {
        JobStatus jobStatus = new JobStatus();
        jobStatus.setId(1L);
        jobStatus.setStatus("IN PROGRESS");

        when(jobStatusRepository.findById(1L)).thenReturn(Optional.of(jobStatus));
        when(file.getInputStream()).thenThrow(new IOException("Invalid file format"));

        assertThrows(IOException.class, () -> importService.importXLSFile(file, 1L));
    }

    @Test
    void testImportWithEmptyFile() throws IOException {
        JobStatus jobStatus = new JobStatus();
        jobStatus.setId(1L);
        jobStatus.setStatus("IN PROGRESS");

        when(jobStatusRepository.findById(1L)).thenReturn(Optional.of(jobStatus));
        when(file.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[0]));

        assertThrows(org.apache.poi.EmptyFileException.class, () -> importService.importXLSFile(file, 1L));
    }
}
