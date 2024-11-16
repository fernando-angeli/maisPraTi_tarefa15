package com.atividade15.api_alunos.service;

import com.atividade15.api_alunos.dto.*;
import com.atividade15.api_alunos.mapper.EntityMapper;
import com.atividade15.api_alunos.model.Student;
import com.atividade15.api_alunos.model.Course;
import com.atividade15.api_alunos.repository.StudentRepository;
import com.atividade15.api_alunos.repository.CourseRepository;
import com.atividade15.api_alunos.service.exceptions.DatabaseException;
import com.atividade15.api_alunos.service.exceptions.EnrollException;
import com.atividade15.api_alunos.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.atividade15.api_alunos.mapper.EntityMapper.*;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public String enrollStudent(Long studentId, Long courseId){
        Student student = getStudentById(studentId);

        student.getCourses().forEach(c -> {
            if(c.getId().equals(courseId))
                throw new EnrollException("Estudante já matriculado para este curso.");
        });

        Course course = getCourseById(courseId);

        student.getCourses().add(course);
        studentRepository.save(student);

        return student.getName() + " foi matriculado no curso " + course.getName();
    }

    public StudentResponseDto insertStudent(StudentRequestDto dto){
        verifyExistsEmail(dto.getEmail());
        Student student = EntityMapper.convertToEntity(dto, Student.class);
        student = studentRepository.save(student);
        return convertToDto(student, StudentResponseDto.class);
    }

    public CourseResponseDto insertCourse(CourseRequestDto dto){
        Course course = new Course();
        course = convertToEntity(dto, Course.class);
        course = courseRepository.save(course);
        return convertToDto(course, CourseResponseDto.class);
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

    public Student getStudentById(Long id){
        return studentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Aluno de id " + id + " não localizado."));
    }

    public Course getCourseById(Long id){
        return courseRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Curso de id " + id + " não localizado."));
    }

    public List<StudentResponseDto> listCourseStudents(Long courseId) {
        Course course = getCourseById(courseId);
        return course.getStudents().stream().filter(student -> student.getCourses().stream()
                .anyMatch(c -> c.getId().equals(courseId)))
                .map(student -> convertToDto(student, StudentResponseDto.class))
                .toList();
    }

    public List<CourseResponseDto> listStudentCourses(Long studentId) {
        Student student = getStudentById(studentId);
        return student.getCourses().stream().map(course -> convertToDto(course, CourseResponseDto.class)).toList();
    }

    public void verifyExistsEmail(String email){
        Optional<Student> student = studentRepository.findByEmail(email);
        if(student.isPresent())
            throw new DatabaseException("E-mail já utilizado.");
    }

    public void cancelEnroll(Long id, Long cursoId) {
        Student student = getStudentById(id);
        Course course = getCourseById(cursoId);
        student.getCourses().remove(course);
        studentRepository.save(student);
    }

    public CourseResponseDto findCourseByName(String name) {
        Course course = courseRepository.findByName(name)
            .orElseThrow(() -> new ResourceNotFoundException("Curso não localizado."));
        return convertToDto(course, CourseResponseDto.class);
    }

    public StudentResponseDto findStudentByEmail(String email) {
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Estudante não localizado."));
        return convertToDto(student, StudentResponseDto.class);
    }

}
