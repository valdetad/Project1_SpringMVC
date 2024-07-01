package com.example.Project1_SpringMVC.service;

import com.example.Project1_SpringMVC.data.models.Subject;
import com.example.Project1_SpringMVC.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Subject getSubjectById(int subjectId) {
        Optional<Subject> subject = subjectRepository.findById((long) subjectId);
        return subject.orElse(null);
    }

    public void deleteSubject(int subjectId) {
        subjectRepository.deleteById((long) subjectId);
    }

    public Subject saveOrUpdateSubject(Subject subject) {
        return subjectRepository.save(subject);
    }
}
