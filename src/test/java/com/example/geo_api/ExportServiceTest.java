package com.example.geo_api;

import com.example.geo_api.entity.JobStatus;
import com.example.geo_api.entity.Section;
import com.example.geo_api.repository.JobStatusRepository;
import com.example.geo_api.service.ExportService;
import com.example.geo_api.service.SectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ExportServiceTest {


    @Mock
    private SectionService sectionService;

    @Mock
    private JobStatusRepository jobStatusRepository;

    @InjectMocks
    private ExportService exportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExportToXLS() throws IOException {
        JobStatus jobStatus = new JobStatus();
        jobStatus.setId(1L);
        jobStatus.setStatus("IN PROGRESS");

        when(jobStatusRepository.findById(1L)).thenReturn(java.util.Optional.of(jobStatus));
        when(sectionService.getAllSections()).thenReturn(Collections.singletonList(new Section("Section 1")));

        exportService.exportToXLS(1L);

        verify(jobStatusRepository, times(1)).save(any(JobStatus.class));
    }

    @Test
    void testGetExportedFile_FileNotFound() {
        assertThrows(IOException.class, () -> exportService.getExportedFile(1L));
    }

    @Test
    void testExportWithNoSections() throws IOException {
        JobStatus jobStatus = new JobStatus();
        jobStatus.setId(1L);
        jobStatus.setStatus("IN PROGRESS");

        when(jobStatusRepository.findById(1L)).thenReturn(java.util.Optional.of(jobStatus));
        when(sectionService.getAllSections()).thenReturn(Collections.emptyList());

        exportService.exportToXLS(1L);

        verify(jobStatusRepository, times(1)).save(any(JobStatus.class));
    }

    @Test
    void testExportWithLargeNumberOfSections() throws IOException {
        JobStatus jobStatus = new JobStatus();
        jobStatus.setId(1L);
        jobStatus.setStatus("IN PROGRESS");

        List<Section> sections = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            sections.add(new Section("Section " + i));
        }

        when(jobStatusRepository.findById(1L)).thenReturn(java.util.Optional.of(jobStatus));
        when(sectionService.getAllSections()).thenReturn(sections);

        exportService.exportToXLS(1L);

        verify(jobStatusRepository, times(1)).save(any(JobStatus.class));
    }
}
