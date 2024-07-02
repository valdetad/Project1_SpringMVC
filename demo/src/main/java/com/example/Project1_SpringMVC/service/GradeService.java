package com.example.Project1_SpringMVC.service;
import com.example.Project1_SpringMVC.data.models.Grade;
import com.example.Project1_SpringMVC.repository.GradeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GradeService {

    private GradeRepository gradeRepository;

    public GradeService(GradeRepository gradeRepository){
        this.gradeRepository = gradeRepository;
    }

    public List <Grade> getAllGrade(){
        return gradeRepository.findAll();
    }

    public Grade getGradeById(int gradeId){
        Optional<Grade> grade = gradeRepository.findById((long) gradeId);
        return grade.orElse(null); }

    public void deleteGrade(int gradeId){
        gradeRepository.deleteById((long) gradeId);
    }

    public void saveOrUpdateGrade(Grade grade){
        gradeRepository.save(grade);
    }
}
