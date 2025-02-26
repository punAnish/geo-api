package com.example.geo_api.controller;

import com.example.geo_api.entity.Section;
import com.example.geo_api.service.SectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sections")
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    // get all sections
    @GetMapping
    public List<Section> getAllSections() {
        return sectionService.getAllSections();
    }

    // get section by id
    @GetMapping("/{id}")
    public Optional<Section> getSectionById(@PathVariable Long id) {
        return sectionService.getSectionById(id);
    }

    // create section
    @PostMapping
    public ResponseEntity<Section> createSection(@RequestBody Section section) {
        try {
            Section savedSection = sectionService.saveSection(section);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSection);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Delete section by id
    @DeleteMapping("/{id}")
    public void deleteSectionById(@PathVariable Long id) {
        sectionService.deleteSectionById(id);
    }

    // get sections by geological class code
    @GetMapping("/by-code")
    public List<Section> getSectionsByGeologicalClassCode(@RequestParam String code) {
        return sectionService.getSectionsByGeologicalClassCode(code);
    }
}
