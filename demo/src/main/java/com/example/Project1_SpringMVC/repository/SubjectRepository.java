package com.example.Project1_SpringMVC.repository;

import com.example.Project1_SpringMVC.data.models.Subject;
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
public interface SubjectRepository extends JpaRepository<Subject, Long>, JpaSpecificationExecutor<Subject> {

    static Specification<Subject> filterSubjects(Integer subjectId, Integer studentGroupId) {
        return (Root<Subject> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (subjectId != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), subjectId));
            }

            if (studentGroupId != null) {
                predicates.add(criteriaBuilder.exists(
                        query.subquery(Subject.class)
                                .select(root)
                                .where(criteriaBuilder.equal(root.join("students").get("studentGroup").get("id"), studentGroupId))
                ));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
