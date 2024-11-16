package com.atividade15.api_alunos.repository;

import com.atividade15.api_alunos.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository <Student, Long> {

    Optional<Student> findByEmail(String email);
}
