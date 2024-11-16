package com.atividade15.api_alunos.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StudentResponseDto {

    private Long id;
    private String name;
    private String email;
    private List<CourseResponseDto> courses = new ArrayList<>();

}
