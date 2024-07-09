package com.example.Project1_SpringMVC.service;

import com.example.Project1_SpringMVC.data.dtos.GradeCreateDto;
import com.example.Project1_SpringMVC.data.models.Grade;
import com.example.Project1_SpringMVC.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    public Grade getGradeById(int gradeId) {
        Optional<Grade> grade = gradeRepository.findById(gradeId);
        return grade.orElse(null);
    }

    public void deleteGrade(int gradeId) {
        gradeRepository.deleteById(gradeId);
    }

    public void saveOrUpdateGrade(GradeCreateDto gradeDto) {
        Grade grade = new Grade();
        grade.setGradeValue(gradeDto.getGradeValue());
        gradeRepository.save(grade);
    }

    public void saveOrUpdateGrade(GradeCreateDto gradeDto, Integer id) {
        Optional<Grade> optionalGrade = gradeRepository.findById(id);
        if (optionalGrade.isPresent()) {
            Grade grade = optionalGrade.get();
            grade.setGradeValue(gradeDto.getGradeValue());
            gradeRepository.save(grade);
        }
    }
}
