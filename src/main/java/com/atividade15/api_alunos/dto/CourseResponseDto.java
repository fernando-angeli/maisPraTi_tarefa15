package com.atividade15.api_alunos.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CourseResponseDto {

    private Long id;
    private String name;
    private String description;
    private List<StudentResponseDto> students = new ArrayList<>();

}
