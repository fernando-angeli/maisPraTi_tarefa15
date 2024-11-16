package com.atividade15.api_alunos.repository;

import com.atividade15.api_alunos.model.Student;
import com.atividade15.api_alunos.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository <Course, Long> {

    Optional<Student> findByName(String name);

}
