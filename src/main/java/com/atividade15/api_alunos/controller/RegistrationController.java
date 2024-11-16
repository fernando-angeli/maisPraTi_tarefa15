package com.atividade15.api_alunos.controller;

import com.atividade15.api_alunos.dto.StudentResponseDto;
import com.atividade15.api_alunos.dto.RegistrationDto;
import com.atividade15.api_alunos.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/matricula")
public class RegistrationController {

    @Autowired
    private RegistrationService service;

    @PostMapping
    public ResponseEntity<String> insert(@RequestBody RegistrationDto dto){
        return ResponseEntity.ok().body(service.insert(dto));
    }

    @GetMapping(value = "/curso/{id}")
    public ResponseEntity<List<StudentResponseDto>> listCourseStudents(@PathVariable Long id){
        return ResponseEntity.ok().body(service.listCourseStudents(id));
    }

}
