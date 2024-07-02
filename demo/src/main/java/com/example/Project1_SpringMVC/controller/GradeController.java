package com.example.Project1_SpringMVC.controller;
import com.example.Project1_SpringMVC.data.models.Grade;
import com.example.Project1_SpringMVC.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/grade")

public class GradeController {

    @Autowired
    private GradeService gradeService;

    @GetMapping
    public String getAllGrades(Model model) {
        List<Grade> grades = gradeService.getAllGrade();
        model.addAttribute("grades", grades);
        model.addAttribute("grade", new Grade());
        return "grade";
    }

    @GetMapping("/edit/{id}")
    public String editGrade(@PathVariable("id") int id, Model model) {
        Optional<Grade> grade = Optional.ofNullable(gradeService.getGradeById(id));
        if (grade.isPresent()) {
            model.addAttribute("grade", grade.get());
            return "edit-grade";
        } else {
            return "redirect:/grade";
        }
    }

    @PostMapping
    public String saveGrade(@ModelAttribute("grade") Grade grade) {
        gradeService.saveOrUpdateGrade(grade);
        return "redirect:/grade";
    }

    @PostMapping("/edit/{id}")
    public String updateGrade(@PathVariable("id") int id, @ModelAttribute("grade") Grade grade) {
        grade.setId(id);
        gradeService.saveOrUpdateGrade(grade);
        return "redirect:/grade";
    }

    @GetMapping("/delete/{id}")
    public String deleteGrade(@PathVariable("id") int id) {
        gradeService.deleteGrade(id);
        return "redirect:/grade";
    }
}
