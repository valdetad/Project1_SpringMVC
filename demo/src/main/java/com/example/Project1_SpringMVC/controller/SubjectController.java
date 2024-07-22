package com.example.Project1_SpringMVC.controller;

import com.example.Project1_SpringMVC.data.dtos.SubjectCreateDto;
import com.example.Project1_SpringMVC.data.models.Subject;
import com.example.Project1_SpringMVC.data.models.StudentGroup;
import com.example.Project1_SpringMVC.service.SubjectService;
import com.example.Project1_SpringMVC.service.StudentGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private StudentGroupService studentGroupService;

    @ResponseBody
    @GetMapping("/rest/all")
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping
    public String getAllSubjects(Model model) {
        List<Subject> subjects = subjectService.getAllSubjects();
        List<StudentGroup> studentGroups = studentGroupService.getAllStudentGroups();
        model.addAttribute("subjects", subjects);
        model.addAttribute("studentGroups", studentGroups);
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

    // REST Add 201 Created
    @ResponseBody
    @PostMapping("/rest/add")
    public ResponseEntity<Subject> addSubjectRest(@RequestBody SubjectCreateDto subjectCreateDto) {
        Subject savedSubject = subjectService.saveOrUpdateSubject(subjectCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSubject);
    }

    @GetMapping("/edit/{id}")
    public String showEditSubjectForm(@PathVariable("id") int id, Model model) {
        Subject subject = subjectService.getSubjectById(id);
        if (subject != null) {
            SubjectCreateDto subjectDto = new SubjectCreateDto();
            subjectDto.setName(subject.getName());
            subjectDto.setId(subject.getId());
            model.addAttribute("subject", subjectDto);
            return "edit-subject";
        } else {
            return "redirect:/subject";
        }
    }

    // REST Edit
    @ResponseBody
    @PostMapping("/rest/edit/{id}")
    public ResponseEntity<?> editSubjectRest(@PathVariable("id") int id, @RequestBody SubjectCreateDto subjectDto) {
        Subject updatedSubject = subjectService.saveOrUpdateSubject(subjectDto);

        if (updatedSubject != null) {
            return ResponseEntity.ok(updatedSubject); // 200 OK
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    // REST Edit
    @PostMapping("/edit/{id}")
    public String editSubject(@PathVariable("id") int id, @ModelAttribute("subject") SubjectCreateDto subject) {
        Subject updatedSubject = subjectService.saveOrUpdateSubject(subject);
        if (updatedSubject != null) {
            return "redirect:/subject"; // 200 OK
        } else {
            return "redirect:/subject/edit/" + id;
        }
    }

    // REST Delete
    @ResponseBody
    @DeleteMapping("/rest/delete/{id}")
    public ResponseEntity<Void> deleteSubjectRest(@PathVariable("id") int id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @GetMapping("/delete/{id}")
    public String deleteSubject(@PathVariable("id") int id) {
        subjectService.deleteSubject(id);
        return "redirect:/subject";
    }
}
