package com.example.Project1_SpringMVC.controller;
import com.example.Project1_SpringMVC.data.dtos.SubjectCreateDto;
import com.example.Project1_SpringMVC.data.models.Subject;
import com.example.Project1_SpringMVC.data.models.StudentGroup;
import com.example.Project1_SpringMVC.service.StudentGroupService;
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

    @Autowired
    private StudentGroupService studentGroupService;

    @ResponseBody
    @GetMapping("/rest/all")
    public List<Subject> getAllSubjectsRest() {
        return subjectService.getAllSubjects();
    }

    @ResponseBody
    @PostMapping("/rest/add")
    public ResponseEntity<?> addSubjectRest(@RequestBody SubjectCreateDto subjectCreateDto) {
        try {
            Subject savedSubject = subjectService.saveOrUpdateSubject(subjectCreateDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSubject);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ResponseBody
    @PostMapping("/rest/edit/{id}")
    public ResponseEntity<?> editSubjectRest(@PathVariable("id") int id, @RequestBody SubjectCreateDto subjectDto) {
        try {
            Subject updatedSubject = subjectService.saveOrUpdateSubject(subjectDto);
            if (updatedSubject != null) {
                return ResponseEntity.ok(updatedSubject); // 200 OK
            } else {
                return ResponseEntity.notFound().build(); // 404 Not Found
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ResponseBody
    @DeleteMapping("/rest/delete/{id}")
    public ResponseEntity<Void> deleteSubjectRest(@PathVariable("id") int id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @GetMapping
    public String getAllSubjects(@RequestParam(required = false) Integer subjectId,
                                 @RequestParam(required = false) Integer studentGroupId,
                                 Model model) {
        List<Subject> subjects = subjectService.getAllSubjects();
        List<Subject> filteredSubjects;

        if (subjectId != null) {
            Subject subject = subjectService.getSubjectById(subjectId);
            filteredSubjects = subject != null ? List.of(subject) : List.of();
        } else if (studentGroupId != null) {
            // Implement filtering based on studentGroupId
            filteredSubjects = subjects.stream()
                    .filter(subject -> subject.getStudents().stream()
                            .anyMatch(student -> student.getStudentGroup().getId().equals(studentGroupId)))
                    .toList();
        } else {
            filteredSubjects = subjects; // No filter applied, display all subjects
        }

        List<StudentGroup> studentGroups = studentGroupService.getAllStudentGroups();
        model.addAttribute("allSubjects", subjects);
        model.addAttribute("subjects", filteredSubjects);
        model.addAttribute("studentGroups", studentGroups);
        model.addAttribute("newSubject", new SubjectCreateDto());
        return "subject";
    }

    @PostMapping
    public String saveSubject(@ModelAttribute("subject") SubjectCreateDto subject, Model model) {
        try {
            subjectService.saveOrUpdateSubject(subject);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("studentGroups", studentGroupService.getAllStudentGroups());
            return "subject";
        }
        return "redirect:/subject";
    }

    @GetMapping("/add")
    public String showSubjectForm(Model model) {
        model.addAttribute("newSubject", new SubjectCreateDto());
        return "add-subject";
    }

    @PostMapping("/add")
    public String addSubject(@ModelAttribute("newSubject") SubjectCreateDto subjectCreateDto, Model model) {
        try {
            subjectService.saveOrUpdateSubject(subjectCreateDto);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "add-subject";
        }
        return "redirect:/subject";
    }

    @GetMapping("/edit/{id}")
    public String showEditSubjectForm(@PathVariable("id") int id, Model model) {
        Subject subject = subjectService.getSubjectById(id);
        if (subject != null) {
            SubjectCreateDto subjectDto = new SubjectCreateDto();
            subjectDto.setName(subject.getName());
            subjectDto.setId(subject.getId());
            List<StudentGroup> studentGroups = studentGroupService.getAllStudentGroups();
            model.addAttribute("subject", subjectDto);
            model.addAttribute("studentGroups", studentGroups);
            return "edit-subject";
        } else {
            return "redirect:/subject";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateSubject(@PathVariable("id") int id, @ModelAttribute("subject") SubjectCreateDto subject, Model model) {
        try {
            subjectService.saveOrUpdateSubject(subject);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("studentGroups", studentGroupService.getAllStudentGroups());
            return "edit-subject";
        }
        return "redirect:/subject";
    }

    @GetMapping("/delete/{id}")
    public String deleteSubject(@PathVariable("id") int id) {
        subjectService.deleteSubject(id);
        return "redirect:/subject";
    }

    @GetMapping("/view/{id}")
    public String viewSubject(@PathVariable("id") int id, Model model) {
        Subject subject = subjectService.getSubjectById(id);
        if (subject != null) {
            List<StudentGroup> studentGroups = studentGroupService.getAllStudentGroups();
            model.addAttribute("subject", subject);
            model.addAttribute("studentGroups", studentGroups);
            return "view-subject";
        } else {
            return "redirect:/subject";
        }
    }
}
