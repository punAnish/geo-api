package com.example.geo_api.service;

import org.springframework.stereotype.Service;
import com.example.geo_api.entity.GeologicalClass;
import com.example.geo_api.repository.GeologicalClassRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GeologicalClassService {
    private final GeologicalClassRepository geologicalClassRepository;


    public GeologicalClassService(GeologicalClassRepository geologicalClassRepository) {
        this.geologicalClassRepository = geologicalClassRepository;
    }

    // get all geological classes
    public List<GeologicalClass> getAllGeologicalClasses() {
        return geologicalClassRepository.findAll();
    }

    // get geological class by id
    public Optional<GeologicalClass> getGeologicalClassById(Long id) {
        return geologicalClassRepository.findById(id);
    }

    // save geological class
    public GeologicalClass saveGeologicalClass(GeologicalClass geologicalClass) {
        return geologicalClassRepository.save(geologicalClass);
    }

    // delete geological class by id
    public void deleteGeologicalClassById(Long id) {
        geologicalClassRepository.deleteById(id);
    }

}
