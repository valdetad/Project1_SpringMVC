package com.example.Project1_SpringMVC.repository;

import com.example.Project1_SpringMVC.data.models.Student;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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

            // Handle search criteria
            if (search != null && !search.isEmpty()) {
                String lowerCaseSearch = search.toLowerCase();
                List<Predicate> searchPredicates = new ArrayList<>();

                // Add OR predicates for search criteria
                searchPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")),
                        "%" + lowerCaseSearch + "%"));
                searchPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")),
                        "%" + lowerCaseSearch + "%"));
                searchPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")),
                        "%" + lowerCaseSearch + "%"));

                // Concatenate firstName and lastName for fullName search
                Predicate fullNamePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(
                                criteriaBuilder.concat(
                                        criteriaBuilder.concat(root.get("firstName"), " "),
                                        root.get("lastName"))),
                        "%" + lowerCaseSearch + "%");
                searchPredicates.add(fullNamePredicate);

                // Combine all search predicates with OR
                predicates.add(criteriaBuilder.or(searchPredicates.toArray(new Predicate[0])));
            }

            // Handle studentGroupId criteria
            if (studentGroupId != null) {
                predicates.add(criteriaBuilder.equal(root.get("studentGroup").get("id"), studentGroupId));
            }

            // Handle subjectId criteria
            if (subjectId != null) {
                // Ensure subjects collection is joined for proper filtering
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
}
