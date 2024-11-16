package com.atividade15.api_alunos.service;

import com.atividade15.api_alunos.dto.StudentRequestDto;
import com.atividade15.api_alunos.dto.StudentResponseDto;
import com.atividade15.api_alunos.dto.RegistrationDto;
import com.atividade15.api_alunos.model.Student;
import com.atividade15.api_alunos.model.Course;
import com.atividade15.api_alunos.repository.StudentRepository;
import com.atividade15.api_alunos.repository.CourseRepository;
import com.atividade15.api_alunos.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.atividade15.api_alunos.mapper.EntityMapper.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public String insert(RegistrationDto dto){
        Student student = getStudentById(dto.getStudentId());
        Course course = getCourseById(dto.getCourseId());
        student.getCourses().add(course);
        studentRepository.save(student);
        return "Matricula realizada.";
    }

    public Optional<StudentResponseDto> findById(Long id) {
        return Optional.ofNullable(convertToDto(getStudentById(id), StudentResponseDto.class));
    }

    public Page<StudentResponseDto> findAll(Pageable pageable) {
        Page<Student> students = studentRepository.findAll(pageable);
        return students.map(student -> convertToDto(student, StudentResponseDto.class));
    }

    public StudentResponseDto update(Long id, StudentRequestDto studentRequestDto){
        getStudentById(id);
        Student studentUpdated = studentRepository.getReferenceById(id);
        convertToEntity(studentRequestDto, studentUpdated);
        studentUpdated = studentRepository.save(studentUpdated);
        return convertToDto(studentUpdated, StudentResponseDto.class);
    }

    public void delete(Long id){
        try{
            getStudentById(id);
            studentRepository.deleteById(id);
        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Aluno de id " + id + " não localizado.");
        }
    }

    public Student getStudentById(Long id){
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno de id " + id + " não localizado."));
    }

    public Course getCourseById(Long id){
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso de id " + id + " não localizado."));
    }

    public List<StudentResponseDto> listCourseStudents(Long courseId) {
        List<StudentResponseDto> students = new ArrayList<>();
        Course course = getCourseById(courseId);
        course.getStudents().stream()
                .filter(student -> student.getCourses().contains(courseId))
                .map(student -> students.add(convertToDto(student, StudentResponseDto.class)));
        return students;
    }
}
