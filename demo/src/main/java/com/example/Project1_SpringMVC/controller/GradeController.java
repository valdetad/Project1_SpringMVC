package com.example.Project1_SpringMVC.controller;
import com.example.Project1_SpringMVC.data.dtos.GradeCreateDto;
import com.example.Project1_SpringMVC.data.models.Grade;
import com.example.Project1_SpringMVC.service.GradeService;
import com.example.Project1_SpringMVC.service.StudentService;
import com.example.Project1_SpringMVC.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private StudentService studentService;

    @GetMapping
    public String getAllGrades(Model model) {
        List<Grade> grades = gradeService.getAllGrades();
        model.addAttribute("grades", grades);
        model.addAttribute("newGrade", new GradeCreateDto());
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "grade";
    }

    @PostMapping
    public String saveGrade(@ModelAttribute("newGrade") GradeCreateDto grade) {
        gradeService.saveOrUpdateGrade(grade, null);
        return "redirect:/grade";
    }

    @GetMapping("/edit/{id}")
    public String editGradeForm(@PathVariable("id") int id, Model model) {
        Grade grade = gradeService.getGradeById(id);
        if (grade != null) {
            GradeCreateDto gradeDto = new GradeCreateDto();
            gradeDto.setGradeValue(grade.getGradeValue());
            gradeDto.setStudentId(grade.getStudent().getId());
            gradeDto.setSubjectId(grade.getSubject().getId());
            model.addAttribute("grade", gradeDto);
            model.addAttribute("students", studentService.getAllStudents());
            model.addAttribute("subjects", subjectService.getAllSubjects());
            return "edit-grade";
        } else {
            return "redirect:/grade";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateGrade(@PathVariable("id") int id, @ModelAttribute("grade") GradeCreateDto grade) {gradeService.saveOrUpdateGrade(grade, id);
        return "redirect:/grade";
    }

    @GetMapping("/delete/{id}")
    public String deleteGrade(@PathVariable("id") int id) {
        gradeService.deleteGrade(id);
        return "redirect:/grade";
    }
}