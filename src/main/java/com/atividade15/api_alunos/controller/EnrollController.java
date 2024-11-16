package com.atividade15.api_alunos.controller;

import com.atividade15.api_alunos.dto.*;
import com.atividade15.api_alunos.service.EnrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class EnrollController {

    @Autowired
    private EnrollService enrollService;

    @PostMapping(value = "/alunos")
    public ResponseEntity<StudentResponseDto> insertStudent(@RequestBody StudentRequestDto dto){
        StudentResponseDto newStudent = enrollService.insertStudent(dto);
        return ResponseEntity.ok().body(newStudent);
    }

    @PostMapping(value = "/cursos")
    public ResponseEntity<CourseResponseDto> insertCourse(@RequestBody CourseRequestDto dto){
        CourseResponseDto newCourse = enrollService.insertCourse(dto);
        return ResponseEntity.ok().body(newCourse);
    }

    @PostMapping(value = "/alunos/{id}/cursos/{cursoId}")
    public ResponseEntity<String> enrollStudent(@PathVariable Long id, @PathVariable Long cursoId){
        String response = enrollService.enrollStudent(id, cursoId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/curso/{id}")
    public ResponseEntity<List<StudentResponseDto>> listCourseStudents(@PathVariable Long id){
        return ResponseEntity.ok().body(enrollService.listCourseStudents(id));
    }

    @GetMapping(value = "/curso")
    public ResponseEntity<CourseResponseDto> findCourseByName(@RequestParam String name){
        return ResponseEntity.ok().body(enrollService.findCourseByName(name));
    }

    @GetMapping(value = "/aluno/{id}")
    public ResponseEntity<List<CourseResponseDto>> listStudentCourses(@PathVariable Long id){
        return ResponseEntity.ok().body(enrollService.listStudentCourses(id));
    }

    @GetMapping(value = "/aluno")
    public ResponseEntity<StudentResponseDto> findStudentByEmail(@RequestParam String email){
        return ResponseEntity.ok().body(enrollService.findStudentByEmail(email));
    }

    @DeleteMapping(value = "/alunos/{id}/cursos/{cursoId}")
    public ResponseEntity<Void> cancelEnroll(@PathVariable Long id, @PathVariable Long cursoId){
        enrollService.cancelEnroll(id, cursoId);
        return ResponseEntity.noContent().build();
    }

}
