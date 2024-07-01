package com.example.Project1_SpringMVC.controller;

import com.example.Project1_SpringMVC.data.models.Subject;
import com.example.Project1_SpringMVC.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/subject")
    public String getAllSubjects(Model model) {
        List<Subject> subjects = subjectService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        model.addAttribute("newSubject", new Subject());
        return "subjects";
    }

    @PostMapping("/subject")
    public String addSubject(@ModelAttribute("subject") Subject subject) {
        subjectService.saveOrUpdateSubject(subject);
        return "redirect:/subject";
    }

    @GetMapping("/subject/edit/{id}")
    public String editSubjectForm(@PathVariable("id") int id, Model model) {
        Optional<Subject> subject = Optional.ofNullable(subjectService.getSubjectById(id));
        if (subject.isPresent()) {
            model.addAttribute("subject", subject.get());
            return "edit-subject";
        } else {
            return "redirect:/subject";
        }
    }

    @PostMapping("/subject/edit/{id}")
    public String updateSubject(@PathVariable("id") int id, @ModelAttribute("subject") Subject subject) {
        subject.setId(id);
        subjectService.saveOrUpdateSubject(subject);
        return "redirect:/subject";
    }

    @GetMapping("/subject/delete/{id}")
    public String deleteSubject(@PathVariable("id") int id) {
        subjectService.deleteSubject(id);
        return "redirect:/subject";
    }
}
