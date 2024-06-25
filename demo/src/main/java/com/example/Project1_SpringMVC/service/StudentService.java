package com.example.Project1_SpringMVC.service;
import com.example.Project1_SpringMVC.data.models.Student;
import com.example.Project1_SpringMVC.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(int studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        return student.orElse(null); }

    public void deleteStudent(int studentId) {
        studentRepository.deleteById(studentId);
    }

    public void saveOrUpdateStudent(Student student) {
        studentRepository.save(student);
    }
}