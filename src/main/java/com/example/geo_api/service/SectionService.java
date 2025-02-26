package com.example.geo_api.service;

import com.example.geo_api.entity.GeologicalClass;
import com.example.geo_api.entity.Section;
import com.example.geo_api.repository.SectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectionService {
    private final SectionRepository sectionRepository;

    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    // get all sections
    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    // get section by id
    public Optional<Section> getSectionById(Long id) {
        return sectionRepository.findById(id);
    }

    // save section
    public Section saveSection(Section section) {
        // Check if a section with the same name already exists
        if (sectionRepository.existsByName(section.getName())) {
            throw new IllegalArgumentException("A section with the name '" + section.getName() + "' already exists.");
        }
        // Check if the geological classes are not null
        if (section.getGeologicalClasses() != null) {
            for (GeologicalClass geologicalClass : section.getGeologicalClasses()) {
                geologicalClass.setSection(section);
            }
        }
        return sectionRepository.save(section);
    }

    // delete section by id
    public void deleteSectionById(Long id) {
        sectionRepository.deleteById(id);
    }

    // get sections by geological class code
    public List<Section> getSectionsByGeologicalClassCode(String code) {
        return sectionRepository.findByGeologicalClassesCode(code);
    }

    // get section by name
    public Section findByName(String name) {
        return sectionRepository.findByName(name);
    }
}
