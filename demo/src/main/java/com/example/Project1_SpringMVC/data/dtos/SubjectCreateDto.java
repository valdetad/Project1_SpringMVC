package com.example.Project1_SpringMVC.data.dtos;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectCreateDto {
    @Setter
    @Getter
    private Integer id;
    private String name;
    private List<Long> studentsIds;
    private List<Long> subjectIds;

}
