package com.atividade15.api_alunos.service;

import com.atividade15.api_alunos.dto.CourseRequestDto;
import com.atividade15.api_alunos.dto.CourseResponseDto;
import com.atividade15.api_alunos.dto.StudentResponseDto;
import com.atividade15.api_alunos.model.Course;
import com.atividade15.api_alunos.repository.CourseRepository;
import com.atividade15.api_alunos.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.atividade15.api_alunos.mapper.EntityMapper.*;

@Service
public class CourseService {

    @Autowired
    private CourseRepository repository;

    @Autowired
    private StudentService studentService;

    public CourseResponseDto insert(CourseRequestDto dto){
        Course course = new Course();
        course = convertToEntity(dto, Course.class);
        course = repository.save(course);
        return convertToDto(course, CourseResponseDto.class);
    }

    public Optional<CourseResponseDto> findById(Long id) {
        Course course = getById(id);
        CourseResponseDto responseDto = convertToDto(course, CourseResponseDto.class);
        List<StudentResponseDto> students =
                studentService
                        .findAll().stream()
                        .filter(student -> student.getCourses().contains(course.getId() == id)).toList();
        responseDto.setStudents(students);
        return Optional.ofNullable(convertToDto(getById(id), CourseResponseDto.class));
    }

    public List<CourseResponseDto> findAll() {
        List<Course> courses = repository.findAll();
        return courses.stream().map(student -> convertToDto(student, CourseResponseDto.class)).collect(Collectors.toList());
    }

    public CourseResponseDto update(Long id, CourseRequestDto courseRequestDto){
        getById(id);
        Course courseUpdated = repository.getReferenceById(id);
        convertToEntity(courseRequestDto, courseUpdated);
        courseUpdated = repository.save(courseUpdated);
        return convertToDto(courseUpdated, CourseResponseDto.class);
    }

    public void delete(Long id){
        try{
            getById(id);
            repository.deleteById(id);
        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Curso de id " + id + " não localizado.");
        }
    }

    public Course getById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso de id " + id + " não localizado."));
    }

}
