package com.example.geo_api.service;

import com.example.geo_api.entity.GeologicalClass;
import com.example.geo_api.entity.JobStatus;
import com.example.geo_api.entity.Section;
import com.example.geo_api.repository.JobStatusRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ExportService {

    private final SectionService sectionService;
    private final JobStatusRepository jobStatusRepository;
    private static final String EXPORT_DIR = "exported_files/";

    public ExportService(SectionService sectionService, JobStatusRepository jobStatusRepository) {
        this.sectionService = sectionService;
        this.jobStatusRepository = jobStatusRepository;
    }

    // export data to XLS file
    @Async
    public void exportToXLS(Long jobId) {
        JobStatus jobStatus = jobStatusRepository.findById(jobId).orElseThrow();
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sections");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Section name");

            // Create headers for up to 10 classes
            int maxClasses = 10;
            int cellIndex = 1;
            for (int i = 1; i <= maxClasses; i++) {
                headerRow.createCell(cellIndex++).setCellValue("Class " + i + " name");
                headerRow.createCell(cellIndex++).setCellValue("Class " + i + " code");
            }

            int rowNum = 1;
            for (Section section : sectionService.getAllSections()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(section.getName());
                int cellNum = 1;
                for (GeologicalClass geoClass : section.getGeologicalClasses()) {
                    row.createCell(cellNum++).setCellValue(geoClass.getName());
                    row.createCell(cellNum++).setCellValue(geoClass.getCode());
                }
            }

            // Write the workbook content to a byte array
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                workbook.write(outputStream);
                // Save the exported file to the file system
                saveExportedFile(jobId, outputStream.toByteArray());

            }
            jobStatus.setStatus("DONE");
        } catch (IOException e) {
            jobStatus.setStatus("ERROR");
        } finally {
            jobStatusRepository.save(jobStatus);
        }
    }

    // Save the exported file to the file system
    private void saveExportedFile(Long jobId, byte[] fileContent) throws IOException {
        Path exportPath = Paths.get(EXPORT_DIR + jobId + ".xlsx");
        Files.createDirectories(exportPath.getParent());
        Files.write(exportPath, fileContent);
    }

    // Get the exported file from the file system
    public byte[] getExportedFile(Long jobId) throws IOException {
        Path exportPath = Paths.get(EXPORT_DIR + jobId + ".xlsx");
        if (Files.exists(exportPath)) {
            return Files.readAllBytes(exportPath);
        } else {
            throw new IOException("File not found for job ID: " + jobId);
        }
    }

    // export data to XLS file
    public Long exportData(List<Section> sections) {
        JobStatus jobStatus = new JobStatus();
        jobStatus.setStatus("IN PROGRESS");
        jobStatusRepository.save(jobStatus);
        exportToXLS(jobStatus.getId());
        return jobStatus.getId();
    }
}
