package com.example.Project1_SpringMVC.service;

import com.example.Project1_SpringMVC.data.dtos.StudentGroupCreateDto;
import com.example.Project1_SpringMVC.data.models.StudentGroup;
import com.example.Project1_SpringMVC.repository.StudentGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentGroupService {

    @Autowired
    private StudentGroupRepository studentGroupRepository;

    public List<StudentGroup> getAllStudentGroups() {
        return studentGroupRepository.findAll();
    }

    public StudentGroup getStudentGroupById(int id) {
        Optional<StudentGroup> studentGroup = studentGroupRepository.findById((int) id);
        return studentGroup.orElse(null);
    }

    public StudentGroup saveOrUpdateStudentGroup(StudentGroup studentGroup) {
        return studentGroupRepository.save(studentGroup);
    }

    public void deleteStudentGroup(int id) {
        studentGroupRepository.deleteById((int) id);
    }

    public StudentGroup saveOrUpdateStudentGroup(StudentGroupCreateDto studentGroupCreateDto) {
        return null;
    }
}