package com.capstone.studentrating.service;

import com.capstone.studentrating.dto.Student;
import com.capstone.studentrating.dto.Subject;
import com.capstone.studentrating.repository.AssignmentRepository;
import com.capstone.studentrating.repository.DistributionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class StudentRatingServiceIT {

    @Autowired
    DistributionRepository distributionRepository;

    @Autowired
    AssignmentRepository assignmentRepository;

    @Autowired
    StudentRatingService studentRatingService;

    @Test
    @DisplayName("Calculate Rating by Student Name")
    void testCalculateStudentRating() throws Exception {
        //Execute the service call
        List<Subject> response = studentRatingService.calculateStudentRating("Esharath");

        //Assert the response
        Assertions.assertNotNull(response, "Response should not be null");
        Assertions.assertEquals(1, response.size(), "Size should be 1 only");
    }

    @Test
    @DisplayName("Calculate Rating by Subject Name")
    void testCalculateSubjectRating() throws Exception {
        //Execute the service call
        List<Student> response = studentRatingService.calculateSubjectRating("Electro Fields");

        //Assert the response
        Assertions.assertNotNull(response, "Response should not be null");
        Assertions.assertEquals(5, response.size(), "The size should be 5");
    }
}
