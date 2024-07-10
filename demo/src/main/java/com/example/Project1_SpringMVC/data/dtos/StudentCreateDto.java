package com.example.Project1_SpringMVC.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class StudentCreateDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    private LocalDate birthDate;
    private Integer studentGroupId;
    private List<Long> subjectIds;
}