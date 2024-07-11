package com.example.Project1_SpringMVC.controller;

import com.example.Project1_SpringMVC.data.dtos.StudentCreateDto;
import com.example.Project1_SpringMVC.data.models.Student;
import com.example.Project1_SpringMVC.service.StudentGroupService;
import com.example.Project1_SpringMVC.service.StudentService;
import com.example.Project1_SpringMVC.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentGroupService studentGroupService;

    @Autowired
    private SubjectService subjectService;

    @ResponseBody
    @GetMapping("/rest/all")
    public List<Student> getAllStudentsRest() {
        return studentService.getAllStudents();
    }

    // REST Add
    @ResponseBody
    @PostMapping("/rest/add")
    public Student addStudentRest(@RequestBody StudentCreateDto studentCreateDto) {
        return studentService.saveOrUpdateStudent(studentCreateDto, null);
    }

    // REST Edit
    @ResponseBody
    @PostMapping("/rest/edit/{id}")
    public Student editStudentRest(@PathVariable("id") int id, @RequestBody StudentCreateDto studentCreateDto) {
        return studentService.saveOrUpdateStudent(studentCreateDto, id);
    }

    // REST Delete
    @ResponseBody
    @DeleteMapping("/rest/delete/{id}")
    public ResponseEntity<Void> deleteStudentRest(@PathVariable("id") int id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public String getAllStudents(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        model.addAttribute("newStudent", new StudentCreateDto());
        model.addAttribute("studentGroups", studentGroupService.getAllStudentGroups());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "student";
    }

    @PostMapping
    public String saveStudent(@ModelAttribute("student") StudentCreateDto student) {
        studentService.saveOrUpdateStudent(student, null);
        return "redirect:/student";
    }

    @GetMapping("/edit/{id}")
    public String showEditStudentForm(@PathVariable("id") int id, Model model) {
        Student student = studentService.getStudentById(id);
        if (student != null) {
            StudentCreateDto studentDto = new StudentCreateDto();
            studentDto.setId(Long.valueOf(student.getId()));
            studentDto.setFirstName(student.getFirstName());
            studentDto.setLastName(student.getLastName());
            studentDto.setEmail(student.getEmail());
            studentDto.setBirthDate(student.getBirthDate());
            studentDto.setStudentGroupId(student.getStudentGroup().getId());
            model.addAttribute("student", studentDto);
            model.addAttribute("studentGroups", studentGroupService.getAllStudentGroups());
            model.addAttribute("subjects", subjectService.getAllSubjects());
            return "edit-student";
        } else {
            return "redirect:/student";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable("id") int id, @ModelAttribute("student") StudentCreateDto student) {
        studentService.saveOrUpdateStudent(student, id);
        return "redirect:/student";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") int id, Model model) {
        studentService.deleteStudent(id);
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        model.addAttribute("newStudent", new StudentCreateDto());
        model.addAttribute("studentGroups", studentGroupService.getAllStudentGroups());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "redirect:/student";
    }
}
