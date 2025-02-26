package com.example.geo_api.controller;

import com.example.geo_api.entity.GeologicalClass;
import com.example.geo_api.service.GeologicalClassService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/geological-classes")
public class GeologicalClassController {


    private final GeologicalClassService geologicalClassService;

    public GeologicalClassController(GeologicalClassService geologicalClassService) {
        this.geologicalClassService = geologicalClassService;
    }

    // get all geological classes
    @GetMapping
    public List<GeologicalClass> getAllGeologicalClasses() {
        return geologicalClassService.getAllGeologicalClasses();
    }

    // get geological class by id
    @GetMapping("/{id}")
    public ResponseEntity<GeologicalClass> getGeologicalClassById(@PathVariable Long id) {
        return geologicalClassService.getGeologicalClassById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // create geological class
    @PostMapping
    public GeologicalClass createGeologicalClass(@RequestBody GeologicalClass geologicalClass) {
        GeologicalClass savedGeologicalClass = geologicalClassService.saveGeologicalClass(geologicalClass);
        return ResponseEntity.ok(savedGeologicalClass).getBody();
    }

    // Delete geological class by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGeologicalClassById(@PathVariable Long id) {
        geologicalClassService.deleteGeologicalClassById(id);
        return ResponseEntity.ok("Geological class deleted successfully");
    }

}
