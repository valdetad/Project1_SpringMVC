package com.example.Project1_SpringMVC.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class StudentGroupCreateDto {
    private String name;

//    private List<Long> studentsIds; // TODO different screen for students (this is populated by student add/edit)
//
//    private List<Long> subjectIds; // TODO different screen for subjects (not in student group)
}