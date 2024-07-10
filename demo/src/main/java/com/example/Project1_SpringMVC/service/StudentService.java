package com.example.Project1_SpringMVC.service;
import com.example.Project1_SpringMVC.data.dtos.StudentCreateDto;
import com.example.Project1_SpringMVC.data.models.Student;
import com.example.Project1_SpringMVC.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    private StudentGroupService studentGroupService;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(int studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        return student.orElse(null);
    }

    public void deleteStudent(int studentId) {
        studentRepository.deleteById(studentId);
    }

    public Student saveOrUpdateStudent(StudentCreateDto student, Integer id) {
        Student newStudent;
        if(id != null) {
            newStudent = this.getStudentById(id);
        } else {
            newStudent = new Student();
        }
        newStudent.setFirstName(student.getFirstName());
        newStudent.setLastName(student.getLastName());
        newStudent.setEmail(student.getEmail());
        newStudent.setBirthDate(student.getBirthDate());
        newStudent.setStudentGroup(studentGroupService.getStudentGroupById(student.getStudentGroupId()));

//        student.getSubjectIds()
//        TODO update subjects for the student

        studentRepository.save(newStudent);
        return newStudent;
    }
}

