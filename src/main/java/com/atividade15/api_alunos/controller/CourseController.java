package com.atividade15.api_alunos.controller;

import com.atividade15.api_alunos.dto.CourseRequestDto;
import com.atividade15.api_alunos.dto.CourseResponseDto;
import com.atividade15.api_alunos.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/cursos")
public class CourseController {

    @Autowired
    private CourseService service;

    @PostMapping
    public ResponseEntity<CourseResponseDto> insert(@RequestBody CourseRequestDto dto){
        CourseResponseDto newCourse = service.insert(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newCourse.getId())
                .toUri();
        return ResponseEntity.created(uri).body(newCourse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CourseResponseDto>> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<CourseResponseDto>> findAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDto> update(@PathVariable Long id, @RequestBody CourseRequestDto dto){
        return ResponseEntity.ok().body(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
