package com.example.Project1_SpringMVC.repository;
import com.example.Project1_SpringMVC.data.models.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
}
