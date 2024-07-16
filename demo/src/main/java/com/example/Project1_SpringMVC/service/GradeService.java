package com.example.Project1_SpringMVC.service;

import com.example.Project1_SpringMVC.data.dtos.GradeCreateDto;
import com.example.Project1_SpringMVC.data.models.Grade;
import com.example.Project1_SpringMVC.data.models.Student;
import com.example.Project1_SpringMVC.data.models.Subject;
import com.example.Project1_SpringMVC.repository.GradeRepository;
import com.example.Project1_SpringMVC.repository.StudentRepository;
import com.example.Project1_SpringMVC.repository.SubjectRepository;
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

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

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

    public Grade saveOrUpdateGrade(GradeCreateDto gradeCreateDto) {
        Grade grade = mapToGrade(gradeCreateDto);
        return gradeRepository.save(grade);
    }

    public void saveOrUpdateGrade(GradeCreateDto gradeCreateDto, Integer id) {
        Optional<Grade> optionalGrade = gradeRepository.findById(id);
        optionalGrade.ifPresent(grade -> {
            mapToGrade(gradeCreateDto, grade);
            gradeRepository.save(grade);
        });
    }

    private Grade mapToGrade(GradeCreateDto gradeCreateDto) {
        Grade grade = new Grade();
        grade.setGradeValue(gradeCreateDto.getGradeValue());
        setStudentAndSubject(grade, gradeCreateDto.getStudentId(), gradeCreateDto.getSubjectId());
        return grade;
    }

    private void mapToGrade(GradeCreateDto gradeCreateDto, Grade grade) {
        grade.setGradeValue(gradeCreateDto.getGradeValue());
        setStudentAndSubject(grade, gradeCreateDto.getStudentId(), gradeCreateDto.getSubjectId());
    }

    private void setStudentAndSubject(Grade grade, Integer studentId, Integer subjectId) {
        if (studentId != null) {
            Optional<Student> student = studentRepository.findById(studentId);
            student.ifPresent(grade::setStudent);
        }
        if (subjectId != null) {
            Optional<Subject> subject = subjectRepository.findById((long) subjectId);
            subject.ifPresent(grade::setSubject);
        }
    }
}
