package com.capstone.studentrating.controller;

import com.capstone.studentrating.dto.Student;
import com.capstone.studentrating.dto.Subject;
import com.capstone.studentrating.service.StudentRatingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class StudentRatingControllerTest {

    @MockBean
    StudentRatingService studentRatingService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("GET /rating/student/{studentName}")
    void getStudentRating() throws Exception {
        //Setup up our mock service
        Subject subject1 = new Subject("Electro Fields", 40.0, 20.0, 10.0, 30.0, 100.0);
        Subject subject2 = new Subject("Computing Techniques", 34.4, 0.0, 0.0, 0.0, 34.4);
        doReturn(Arrays.asList(subject1, subject2)).when(studentRatingService).calculateStudentRating(anyString());

        //Execute Get request
        mockMvc.perform(get("/rating/student/{studentName}", "Ananth"))
                //Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                //validate returned fields
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].subjectName", is("Electro Fields")))
                .andExpect(jsonPath("$[0].testScore", is(40.0)))
                .andExpect(jsonPath("$[0].quizScore", is(20.0)))
                .andExpect(jsonPath("$[0].labScore", is(10.0)))
                .andExpect(jsonPath("$[0].projectScore", is(30.0)))
                .andExpect(jsonPath("$[0].overallRating", is(100.0)))
                .andExpect(jsonPath("$[1].subjectName", is("Computing Techniques")))
                .andExpect(jsonPath("$[1].testScore", is(34.4)))
                .andExpect(jsonPath("$[1].quizScore", is(0.0)))
                .andExpect(jsonPath("$[1].labScore", is(0.0)))
                .andExpect(jsonPath("$[1].projectScore", is(0.0)))
                .andExpect(jsonPath("$[1].overallRating", is(34.4)));
        verify(studentRatingService).calculateStudentRating(anyString());
    }

    @Test
    @DisplayName("GET /rating/subject/{subjectName}")
    void getSubjectRating() throws Exception {
        //Setup our mock Service
        Student student1 = new Student("Ananth", 34.4, 0.0, 0.0, 0.0, 34.4);
        Student student2 = new Student("Bhagath", 0.0, 0.0, 0.0, 30.0, 30.0);
        Student student3 = new Student("Chaya", 0.0, 4.0, 0.0, 0.0, 4.0);
        doReturn(Arrays.asList(student1, student2, student3)).when(studentRatingService).calculateSubjectRating(anyString());

        //Perform Get Request
        mockMvc.perform(get("/rating/subject/{subjectName}", "Computing Techniques"))
                //Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                //validate returned fields
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].studentName", is("Ananth")))
                .andExpect(jsonPath("$[0].testScore", is(34.4)))
                .andExpect(jsonPath("$[0].quizScore", is(0.0)))
                .andExpect(jsonPath("$[0].labScore", is(0.0)))
                .andExpect(jsonPath("$[0].projectScore", is(0.0)))
                .andExpect(jsonPath("$[0].overallRating", is(34.4)))
                .andExpect(jsonPath("$[1].studentName", is("Bhagath")))
                .andExpect(jsonPath("$[1].testScore", is(0.0)))
                .andExpect(jsonPath("$[1].quizScore", is(0.0)))
                .andExpect(jsonPath("$[1].labScore", is(0.0)))
                .andExpect(jsonPath("$[1].projectScore", is(30.0)))
                .andExpect(jsonPath("$[1].overallRating", is(30.0)))
                .andExpect(jsonPath("$[2].studentName", is("Chaya")))
                .andExpect(jsonPath("$[2].testScore", is(0.0)))
                .andExpect(jsonPath("$[2].quizScore", is(4.0)))
                .andExpect(jsonPath("$[2].labScore", is(0.0)))
                .andExpect(jsonPath("$[2].projectScore", is(0.0)))
                .andExpect(jsonPath("$[2].overallRating", is(4.0)));
        verify(studentRatingService).calculateSubjectRating(anyString());
    }
}