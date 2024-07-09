package com.example.Project1_SpringMVC.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class GradeCreateDto {
    private Double gradeValue;
    private Integer studentId;
    private Integer subjectId;
}
