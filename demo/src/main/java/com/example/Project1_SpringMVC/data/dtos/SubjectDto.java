package com.example.Project1_SpringMVC.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDto {
    private String name;
    private List<Long> studentsIds;
    private List<Long> subjectIds;
}
