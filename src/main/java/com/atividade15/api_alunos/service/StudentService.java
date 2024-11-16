package com.atividade15.api_alunos.service;

import com.atividade15.api_alunos.dto.StudentRequestDto;
import com.atividade15.api_alunos.dto.StudentResponseDto;
import com.atividade15.api_alunos.mapper.EntityMapper;
import com.atividade15.api_alunos.model.Student;
import com.atividade15.api_alunos.repository.StudentRepository;
import com.atividade15.api_alunos.service.exceptions.DatabaseException;
import com.atividade15.api_alunos.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.atividade15.api_alunos.mapper.EntityMapper.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    public StudentResponseDto insert(StudentRequestDto dto){
        verifyExistsEmail(dto.getEmail());
        Student student = EntityMapper.convertToEntity(dto, Student.class);
        student = repository.save(student);
        return convertToDto(student, StudentResponseDto.class);
    }

    public Optional<StudentResponseDto> findById(Long id) {
        return Optional.ofNullable(convertToDto(getById(id), StudentResponseDto.class));
    }

    public List<StudentResponseDto> findAll() {
        List<Student> alunos = repository.findAll();
        return alunos.stream().map(aluno -> convertToDto(aluno, StudentResponseDto.class)).collect(Collectors.toList());
    }

    public StudentResponseDto update(Long id, StudentRequestDto studentRequestDto){
        getById(id);
        Student alunoUpdated = repository.getReferenceById(id);
        convertToEntity(studentRequestDto, alunoUpdated);
        alunoUpdated = repository.save(alunoUpdated);
        return convertToDto(alunoUpdated, StudentResponseDto.class);
    }

    public void delete(Long id){
        try{
            getById(id);
            repository.deleteById(id);
        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Aluno de id " + id + " não localizado.");
        }
    }

    public Student getById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno de id " + id + " não localizado."));
    }

    public void verifyExistsEmail(String email){
        Optional<Student> student = repository.findByEmail(email);
        if(student.isPresent())
            throw new DatabaseException("E-mail já utilizado.");
    }

}
