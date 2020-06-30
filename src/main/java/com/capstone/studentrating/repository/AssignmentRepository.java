package com.capstone.studentrating.repository;

import com.capstone.studentrating.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {

    List<Assignment> findByStudentNameIgnoreCase(String studentName);

    List<Assignment> findBySubjectIgnoreCase(String subject);
}
