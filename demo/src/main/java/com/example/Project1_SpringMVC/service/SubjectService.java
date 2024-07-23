package com.example.Project1_SpringMVC.service;

import com.example.Project1_SpringMVC.data.dtos.SubjectCreateDto;
import com.example.Project1_SpringMVC.data.models.Subject;
import com.example.Project1_SpringMVC.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

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

    public Subject saveOrUpdateSubject(SubjectCreateDto subjectDto) {
        Subject subject;
        if (subjectDto.getId() != null) {
            Optional<Subject> existingSubjectOptional = subjectRepository.findById((long) subjectDto.getId());
            if (existingSubjectOptional.isPresent()) {
                subject = existingSubjectOptional.get();
                subject.setName(subjectDto.getName());
            } else {
                throw new IllegalArgumentException("Subject with id " + subjectDto.getId() + " not found.");
            }
        } else {
            subject = new Subject();
            subject.setName(subjectDto.getName());
        }
        // Handle studentsIds and subjectIds if needed here

        return subjectRepository.save(subject);
    }

    public List<Subject> findFilteredSubjects(Integer subjectId, Integer studentGroupId) {
        Specification<Subject> spec = SubjectRepository.filterSubjects(subjectId, studentGroupId);
        return subjectRepository.findAll(spec);
    }
}
