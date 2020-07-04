package com.capstone.studentrating.service;

import com.capstone.studentrating.dto.Student;
import com.capstone.studentrating.dto.Subject;
import com.capstone.studentrating.model.Assignment;
import com.capstone.studentrating.model.Distribution;
import com.capstone.studentrating.repository.AssignmentRepository;
import com.capstone.studentrating.repository.DistributionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class StudentRatingServiceTest {

    @Autowired
    StudentRatingService studentRatingService;

    @MockBean
    DistributionRepository distributionRepository;

    @MockBean
    AssignmentRepository assignmentRepository;

    @BeforeEach
    void setUp() {
        //Setup our mock repository
        Assignment assignment = new Assignment(1, "Esharath", "Electro Fields", "test_1", LocalDate.of(2016, 6, 21), 87.0);
        List<Assignment> assignmentList = Arrays.asList(assignment);
        Distribution distribution = new Distribution(1, "test", 40.0);
        List<Distribution> distributionList = Arrays.asList(distribution);
        doReturn(assignmentList).when(assignmentRepository).findByStudentNameIgnoreCase(anyString());
        doReturn(assignmentList).when(assignmentRepository).findBySubjectIgnoreCase(anyString());
        doReturn(distributionList).when(distributionRepository).findAll();
    }

    @Test
    @DisplayName("Calculate Rating by Student Name")
    void testCalculateStudentRating() {
        //Execute the Service call
        List<Subject> response = studentRatingService.calculateStudentRating("Esharath");

        //Assert the response
        Assertions.assertEquals(1, response.size(), "Size should be 1 only");
        Assertions.assertEquals("Electro Fields", response.get(0).getSubjectName(), "The subject should be Electro Fields");
        Assertions.assertEquals(34.8, response.get(0).getOverallRating(), "Overall Rating should be 34.8");
    }

    @Test
    @DisplayName("Calculate Rating by Subject Name")
    void testCalculateSubjectRating() {
        //Execute the Service call
        List<Student> response = studentRatingService.calculateSubjectRating("Electro Fields");

        //Assert the response
        Assertions.assertEquals(1, response.size(), "Size should be 1 only");
        Assertions.assertEquals("Esharath", response.get(0).getStudentName(), "Student name should be Esharath");
        Assertions.assertEquals(34.8, response.get(0).getOverallRating(), "Overall Rating should be 34.8");
    }

    @Test
    @DisplayName("Calculate Score")
    void calculateScore() {
        //Setup our mock Repository
        List<Double> scores = Arrays.asList(100.0, 100.0);

        //Execute the Service call
        Double testScore = studentRatingService.calculateScore(scores, 40.0);

        //Assert the response
        Assertions.assertEquals(40.0, testScore, "The Score should be 40.0");
    }
}