package com.example.Project1_SpringMVC.repository;

import com.example.Project1_SpringMVC.data.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findByFirstNameContainingAndLastNameContainingAndEmailContaining(String firstName, String lastName, String email);
    List<Student> findByFirstNameContainingAndLastNameContainingAndEmailContainingAndStudentGroupIdAndSubjectsIdIn(
            String firstName, String lastName, String email, Integer studentGroupId, List<Integer> subjectIds);

}
