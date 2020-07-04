package com.capstone.studentrating.repository;

import com.capstone.studentrating.model.Assignment;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class AssignmentRepositoryIT {

    @Autowired
    AssignmentRepository assignmentRepository;

    @Test
    void findByStudentNameIgnoreCase() {
        //Execute the repository call
        List<Assignment> response = Lists.newArrayList(assignmentRepository.findByStudentNameIgnoreCase("Esharath"));

        //Assert the response
        Assertions.assertEquals(1, response.size(), "Expected only one record");
    }

    @Test
    void findBySubjectIgnoreCase() {
        //Execute the repository call
        List<Assignment> response = Lists.newArrayList(assignmentRepository.findBySubjectIgnoreCase("Electro Fields"));

        //Assert the response
        Assertions.assertEquals(17, response.size(), "Expected 17 records");
    }
}