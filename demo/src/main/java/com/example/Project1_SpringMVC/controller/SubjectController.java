package com.example.Project1_SpringMVC.controller;
import com.example.Project1_SpringMVC.data.dtos.SubjectCreateDto;
import com.example.Project1_SpringMVC.data.models.Student;
import com.example.Project1_SpringMVC.data.models.Subject;
import com.example.Project1_SpringMVC.service.SubjectService;
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

    @ResponseBody
    @GetMapping("/rest/all")
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

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

    //REST Edit
    @ResponseBody
    @PostMapping("/rest/edit")
    public Subject editSubjectRest(@RequestBody SubjectCreateDto subject) {
        return subjectService.saveOrUpdateSubject(subject);
    }

    @PostMapping("/edit/{id}")
    public String editSubject(@PathVariable("id") int id, @ModelAttribute("subject") SubjectCreateDto subject) {
        subjectService.saveOrUpdateSubject(subject);
        return "redirect:/subject";
    }

    //REST Delete
    @ResponseBody
    @DeleteMapping("/rest/delete/{id}")
    public ResponseEntity<Void> deleteSubjectRest(@PathVariable("id") int id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/delete/{id}")
    public String deleteSubject(@PathVariable("id") int id) {
        subjectService.deleteSubject(id);
        return "redirect:/subject";
    }
}
