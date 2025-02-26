package com.example.geo_api.service;

import com.example.geo_api.entity.GeologicalClass;
import com.example.geo_api.entity.JobStatus;
import com.example.geo_api.entity.Section;
import com.example.geo_api.repository.JobStatusRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Iterator;

@Service
public class ImportService {

    private final SectionService sectionService;
    private final GeologicalClassService geologicalClassService;
    private final JobStatusRepository jobStatusRepository;

    public ImportService(SectionService sectionService, GeologicalClassService geologicalClassService, JobStatusRepository jobStatusRepository) {
        this.sectionService = sectionService;
        this.geologicalClassService = geologicalClassService;
        this.jobStatusRepository = jobStatusRepository;
    }

    // Async method to import an XLS file
    @Async
    @Transactional
    public void importXLSFile(MultipartFile file, Long jobId) throws IOException {
        // Retrieve the job status from the database
        JobStatus jobStatus = jobStatusRepository.findById(jobId).orElseThrow();
        try {
            // create a workbook from the file
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();

            // Iterate over the rows in the sheet
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String sectionName = row.getCell(0).getStringCellValue();
                // find the section by name or create a new one
                Section section = sectionService.findByName(sectionName);
                if (section == null) {
                    section = new Section(sectionName);
                    sectionService.saveSection(section);
                    // Iterate over the cells in the row
                    for (int i = 1; i < row.getPhysicalNumberOfCells(); i += 2) {
                        String geoClassName = row.getCell(i).getStringCellValue();
                        String geoClassCode = row.getCell(i + 1).getStringCellValue();
                        GeologicalClass geologicalClass = new GeologicalClass(geoClassName, geoClassCode, section);
                        geologicalClassService.saveGeologicalClass(geologicalClass);
                    }
                }
            }
            workbook.close();
            jobStatus.setStatus("DONE");
        } catch (IOException e) {
            jobStatus.setStatus("ERROR");
            throw e;
        } finally {
            jobStatusRepository.save(jobStatus);
        }
    }
}
