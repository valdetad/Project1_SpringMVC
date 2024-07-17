package com.example.Project1_SpringMVC.controller;

import com.example.Project1_SpringMVC.data.dtos.GradeCreateDto;
import com.example.Project1_SpringMVC.data.models.Grade;
import com.example.Project1_SpringMVC.service.GradeService;
import com.example.Project1_SpringMVC.service.StudentService;
import com.example.Project1_SpringMVC.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ResponseBody
    @GetMapping("/rest/all")
    public List<Grade> getAllGrades() {
        return gradeService.getAllGrades();
    }

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
    public String saveGrade(@ModelAttribute("newGrade") GradeCreateDto gradeDto) {
        gradeService.saveOrUpdateGrade(gradeDto);
        return "redirect:/grade";
    }

    @ResponseBody
    @PostMapping("/rest/add")
    public ResponseEntity<Grade> addGrade(@RequestBody GradeCreateDto gradeCreateDto) {
        Grade savedGrade = gradeService.saveOrUpdateGrade(gradeCreateDto);
        return ResponseEntity.status(201).body(savedGrade);
    }

    @GetMapping("/edit/{id}")
    public String editGradeForm(@PathVariable("id") int id, Model model) {
        Grade grade = gradeService.getGradeById(id);
        if (grade != null) {
            GradeCreateDto gradeDto = new GradeCreateDto();
            gradeDto.setGradeValue(Double.valueOf(grade.getGradeValue()));
            if (grade.getStudent() != null) {
                gradeDto.setStudentId(grade.getStudent().getId());
            }
            if (grade.getSubject() != null) {
                gradeDto.setSubjectId(grade.getSubject().getId());
            }
            model.addAttribute("grade", gradeDto);
            model.addAttribute("students", studentService.getAllStudents());
            model.addAttribute("subjects", subjectService.getAllSubjects());
            return "edit-grade";
        } else {
            return "redirect:/grade";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateGrade(@PathVariable("id") int id, @ModelAttribute("grade") GradeCreateDto gradeDto) {
        gradeService.saveOrUpdateGrade(gradeDto, id);
        return "redirect:/grade";
    }

    // REST Edit
    @PostMapping("/rest/edit/{id}")
    @ResponseBody
    public ResponseEntity<Grade> updateGradeRest(@PathVariable("id") int id, @RequestBody GradeCreateDto gradeDto) {
        Grade updatedGrade = gradeService.saveOrUpdateGrade(gradeDto);
        if (updatedGrade != null) {
            return ResponseEntity.ok(updatedGrade); // 200 OK
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @DeleteMapping("/rest/delete/{id}")
    public ResponseEntity<Void> deleteGradeRest(@PathVariable("id") int id) {
        try {
            gradeService.deleteGrade(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (EmptyResultDataAccessException e) {
            // Handle specific exception for entity not found
            return ResponseEntity.notFound().build(); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteGrade(@PathVariable("id") int id) {
        gradeService.deleteGrade(id);
        return "redirect:/grade";
    }
}
