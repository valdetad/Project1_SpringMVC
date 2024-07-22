package com.example.Project1_SpringMVC.repository;

import com.example.Project1_SpringMVC.data.models.Student;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>, JpaSpecificationExecutor<Student> {
//    @Query("SELECT s FROM Student s WHERE LOWER(s.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(s.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
//    List<Student> findByNameContainingIgnoreCase(@Param("name") String name);
//
//    @Query("SELECT s FROM Student s WHERE (LOWER(s.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(s.lastName) LIKE LOWER(CONCAT('%', :name, '%'))) AND s.studentGroup.id = :groupId")
//    List<Student> findByNameContainingIgnoreCaseAndGroupId(@Param("name") String name, @Param("groupId") Integer groupId);

//    List<Student> findByFirstNameContainingIgnoreCaseOrLastNameContainingAndStudentGroup_Id(String name, String surname, Integer studentGroupId);

//    List<Student> findByStudentGroupId(Integer studentGroupId);
//    List<Student> findBySubjectsId(@Param("subjectId") Integer subjectId);


    static Specification<Student> filterStudents(String search, Integer studentGroupId, Integer subjectId) {
        return ((root, query, criteriaBuilder) -> {
           List<Predicate> predicates = new ArrayList<>();
            if (search != null && !search.isEmpty()) {
                List<Predicate> searchPredicates = new ArrayList<>();

                String lowerCaseSearch = search.toLowerCase();

                // Add OR predicates for search criteria
                searchPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")),
                        "%" + lowerCaseSearch + "%"));
                searchPredicates.add(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + lowerCaseSearch + "%"));
                searchPredicates.add(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + lowerCaseSearch + "%"));

                // Concatenate firstName and lastName with a space in between for fullName
                // search
                Predicate fullNamePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(
                                criteriaBuilder.concat(
                                        criteriaBuilder.concat(root.get("firstName"), " "),
                                        root.get("lastName"))),
                        "%" + lowerCaseSearch + "%");
                searchPredicates.add(fullNamePredicate);

                // Combine all search predicates with OR
                Predicate searchPredicate = criteriaBuilder.or(searchPredicates.toArray(new Predicate[0]));
                predicates.add(searchPredicate);
            }
            if(studentGroupId != null) {
                // TODO be carefully if studentGroup is null skip that (on the root)
                predicates.add(criteriaBuilder.equal(root.get("studentGroup").get("id"), studentGroupId));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
