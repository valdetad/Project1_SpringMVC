package com.example.Project1_SpringMVC.service;
import com.example.Project1_SpringMVC.data.dtos.StudentCreateDto;
import com.example.Project1_SpringMVC.data.models.Student;
import com.example.Project1_SpringMVC.data.models.Subject;
import com.example.Project1_SpringMVC.repository.StudentRepository;
import com.example.Project1_SpringMVC.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    @Autowired
    private StudentGroupService studentGroupService;

    @Autowired
    public StudentService(StudentRepository studentRepository, SubjectRepository subjectRepository) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> filterStudents(String firstName, String lastName, Integer studentGroupId, Integer subjectId) {
        if (firstName != null && !firstName.isEmpty() && lastName != null && !lastName.isEmpty()) {
            return studentRepository.findByFirstNameAndLastName(firstName, lastName);
        } else if (firstName != null && !firstName.isEmpty()) {
            return studentRepository.findByFirstNameAndLastName(firstName, lastName);
        } else if (lastName != null && !lastName.isEmpty()) {
            return studentRepository.findByFirstNameAndLastName(lastName, lastName);
        } else if (studentGroupId != null) {
            return studentRepository.findByStudentGroupId(studentGroupId);
        } else if (subjectId != null) {
            return studentRepository.findBySubjectsId(subjectId);
        } else {
            return getAllStudents();
        }
    }

    public Student getStudentById(int studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        return student.orElse(null);
    }

    public void deleteStudent(int studentId) {
        studentRepository.deleteById(studentId);
    }

    public Student saveOrUpdateStudent(StudentCreateDto studentDto, Integer id) {
        if (studentDto.getSubjectIds() == null || studentDto.getSubjectIds().isEmpty()) {
            throw new IllegalArgumentException("A student must be assigned to at least one subject.");
        }
        Student student;
        if (id != null) {
            student = this.getStudentById(id);
            if (student == null) {
                throw new IllegalArgumentException("Student not found.");
            }
        } else {
            student = new Student();
        }

        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setEmail(studentDto.getEmail());
        student.setBirthDate(studentDto.getBirthDate());
        student.setStudentGroup(studentGroupService.getStudentGroupById(studentDto.getStudentGroupId()));

        List<Subject> subjects = subjectRepository.findAllById(studentDto.getSubjectIds());
        student.setSubjects(subjects);

        studentRepository.save(student);
        return student;
    }
}
