package com.atividade15.api_alunos.controller;

import com.atividade15.api_alunos.dto.StudentRequestDto;
import com.atividade15.api_alunos.dto.StudentResponseDto;
import com.atividade15.api_alunos.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/alunos")
public class StudentController {

    @Autowired
    private StudentService service;

    @PostMapping
    public ResponseEntity<StudentResponseDto> insert(@RequestBody StudentRequestDto dto){
        StudentResponseDto newStudent = service.insert(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newStudent.getId())
                .toUri();
        return ResponseEntity.created(uri).body(newStudent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<StudentResponseDto>> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDto>> findAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDto> update(@PathVariable Long id, @RequestBody StudentRequestDto dto){
        return ResponseEntity.ok().body(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
