package com.example.Project1_SpringMVC.controller;
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
        return "groups";
    }

    @GetMapping("/add")
    public String showAddStudentGroupForm(Model model) {
        model.addAttribute("studentGroup", new StudentGroup());
        return "add-group";
    }

    @PostMapping("/add")
    public String addStudentGroup(@ModelAttribute("studentGroup") StudentGroup studentGroup) {
        studentGroupService.saveOrUpdateStudentGroup(studentGroup);
        return "redirect:/student-group";
    }

    @GetMapping("/edit/{id}")
    public String showEditStudentGroupForm(@PathVariable("id") int id, Model model) {
        StudentGroup existingStudentGroup = studentGroupService.getStudentGroupById(id);
        if (existingStudentGroup != null) {
            model.addAttribute("studentGroup", existingStudentGroup);
            return "edit-group";
        } else {
            return "redirect:/student-group";
        }
    }

    @PostMapping("/edit/{id}")
    public String editStudentGroup(@PathVariable("id") int id, @ModelAttribute("studentGroup") StudentGroup studentGroup) {
        studentGroup.setId(id);
        studentGroupService.saveOrUpdateStudentGroup(studentGroup);
        return "redirect:/student-group";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudentGroup(@PathVariable("id") int id) {
        studentGroupService.deleteStudentGroup(id);
        return "redirect:/student-group";
    }
}
