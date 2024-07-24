package com.example.Project1_SpringMVC.repository;

import com.example.Project1_SpringMVC.data.models.Student;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>, JpaSpecificationExecutor<Student> {

    static Specification<Student> filterStudents(String search, Integer studentGroupId, Integer subjectId) {
        return (Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (search != null && !search.isEmpty()) {
                String lowerCaseSearch = search.toLowerCase();
                List<Predicate> searchPredicates = new ArrayList<>();
                searchPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")),
                        "%" + lowerCaseSearch + "%"));
                searchPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")),
                        "%" + lowerCaseSearch + "%"));
                searchPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")),
                        "%" + lowerCaseSearch + "%"));

                Predicate fullNamePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(
                                criteriaBuilder.concat(
                                        criteriaBuilder.concat(root.get("firstName"), " "),
                                        root.get("lastName"))),
                        "%" + lowerCaseSearch + "%");
                searchPredicates.add(fullNamePredicate);
                predicates.add(criteriaBuilder.or(searchPredicates.toArray(new Predicate[0])));
            }

            if (studentGroupId != null) {
                predicates.add(criteriaBuilder.equal(root.get("studentGroup").get("id"), studentGroupId));
            }

            if (subjectId != null) {
                Predicate subjectPredicate = criteriaBuilder.exists(
                        query.subquery(Student.class)
                                .select(root)
                                .where(criteriaBuilder.equal(root.join("subjects").get("id"), subjectId))
                );
                predicates.add(subjectPredicate);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    Page<Student> findAll(Specification<Student> spec, Pageable pageable);
}
