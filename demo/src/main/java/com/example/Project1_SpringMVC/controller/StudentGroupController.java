package com.example.Project1_SpringMVC.controller;

import com.example.Project1_SpringMVC.data.dtos.StudentGroupCreateDto;
import com.example.Project1_SpringMVC.data.models.StudentGroup;
import com.example.Project1_SpringMVC.service.StudentGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/student-group")
public class StudentGroupController {

    @Autowired
    private StudentGroupService studentGroupService;

    @GetMapping
    public String getAllStudentGroups(Model model) {
        List<StudentGroup> studentGroups = studentGroupService.getAllStudentGroups();
        model.addAttribute("studentGroups", studentGroups);
        model.addAttribute("newStudentGroup", new StudentGroupCreateDto());
        return "groups";
    }

    @GetMapping("/add")
    public String showAddStudentGroupForm(Model model) {
        model.addAttribute("studentGroup", new StudentGroupCreateDto());
        return "add-group";
    }

    @PostMapping("/add")
    public String addStudentGroup(@ModelAttribute("studentGroup") StudentGroupCreateDto studentGroupDto) {
        StudentGroup studentGroup = mapToStudentGroup(studentGroupDto);
        studentGroupService.saveOrUpdateStudentGroup(studentGroup);
        return "redirect:/student-group";
    }

    @GetMapping("/edit/{id}")
    public String showEditStudentGroupForm(@PathVariable("id") int id, Model model) {
        StudentGroup existingStudentGroup = studentGroupService.getStudentGroupById(id);
        if (existingStudentGroup != null) {
            model.addAttribute("studentGroup", mapToStudentGroupCreateDto(existingStudentGroup));
            return "edit-group";
        } else {
            return "redirect:/student-group";
        }
    }

    @PostMapping("/edit/{id}")
    public String editStudentGroup(@PathVariable("id") int id, @ModelAttribute("studentGroup") StudentGroupCreateDto studentGroupDto) {
        StudentGroup studentGroup = mapToStudentGroup(studentGroupDto);
        studentGroup.setId(id);
        studentGroupService.saveOrUpdateStudentGroup(studentGroup);
        return "redirect:/student-group";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudentGroup(@PathVariable("id") int id) {
        studentGroupService.deleteStudentGroup(id);
        return "redirect:/student-group";
    }

    private StudentGroup mapToStudentGroup(StudentGroupCreateDto studentGroupDto) {
        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setName(studentGroupDto.getName());
        return studentGroup;
    }

    private StudentGroupCreateDto mapToStudentGroupCreateDto(StudentGroup studentGroup) {
        StudentGroupCreateDto studentGroupDto = new StudentGroupCreateDto();
        studentGroupDto.setName(studentGroup.getName());
        return studentGroupDto;
    }
}
