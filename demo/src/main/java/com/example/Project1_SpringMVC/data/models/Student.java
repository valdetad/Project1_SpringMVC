package com.example.Project1_SpringMVC.data.models;
import com.example.Project1_SpringMVC.data.dtos.StudentCreateDto;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;


@Data
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name = "student_group_id")
    private StudentGroup studentGroup;

    @ManyToMany
    @JoinTable(
            name = "student_subject",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Subject> subjects;

    @OneToMany(mappedBy = "student")
    private List<Grade> grades;


    public StudentCreateDto mapToCreateDto() {
        StudentCreateDto studentCreateDto = new StudentCreateDto();
        studentCreateDto.setFirstName(this.firstName);
        studentCreateDto.setLastName(this.lastName);
        studentCreateDto.setEmail(this.email);
        studentCreateDto.setBirthDate(this.birthDate);
        studentCreateDto.setStudentGroupId(this.studentGroup.getId());
        return studentCreateDto;
    }
}
