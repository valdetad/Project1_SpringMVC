package com.example.Project1_SpringMVC.controller;

import com.example.Project1_SpringMVC.data.dtos.SubjectCreateDto;
import com.example.Project1_SpringMVC.data.models.Subject;
import com.example.Project1_SpringMVC.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public String getAllSubjects(Model model) {
        List<Subject> subjects = subjectService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        model.addAttribute("newSubject", new SubjectCreateDto());
        return "subject";
    }

    @PostMapping
    public String saveSubject(@ModelAttribute("subject") SubjectCreateDto subject) {
        subjectService.saveOrUpdateSubject(subject);
        return "redirect:/subject";
    }

    @GetMapping("/add")
    public String showSubjectForm(Model model) {
        model.addAttribute("newSubject", new SubjectCreateDto());
        return "add-subject";
    }

    @PostMapping("/add")
    public String addSubject(@ModelAttribute("newSubject") SubjectCreateDto subjectCreateDto) {
        subjectService.saveOrUpdateSubject(subjectCreateDto);
        return "redirect:/subject";
    }

    @GetMapping("/edit/{id}")
    public String showEditSubjectForm(@PathVariable("id") int id, Model model) {
        Subject subject = subjectService.getSubjectById(id);
        if (subject != null) {
            SubjectCreateDto subjectDto = new SubjectCreateDto();
            subjectDto.setName(subject.getName());
            model.addAttribute("subject", subjectDto);
            return "edit-subject";
        } else {
            return "redirect:/subject";
        }
    }

    @PostMapping("/edit/{id}")
    public String editSubject(@PathVariable("id") int id, @ModelAttribute("subject") SubjectCreateDto subject) {subjectService.saveOrUpdateSubject(subject);
        return "redirect:/subject";
    }

    @GetMapping("/delete/{id}")
    public String deleteSubject(@PathVariable("id") int id) {
        subjectService.deleteSubject(id);
        return "redirect:/subject";
    }
}