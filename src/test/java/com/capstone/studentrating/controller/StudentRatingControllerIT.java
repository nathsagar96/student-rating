package com.capstone.studentrating.controller;

import com.capstone.studentrating.dto.Student;
import com.capstone.studentrating.dto.Subject;
import com.capstone.studentrating.service.StudentRatingService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class StudentRatingControllerIT {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    StudentRatingService studentRatingService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Get Rating for Student")
    void testGetStudentRating() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/rating/student/{studentName}", "Ananth"))
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
                .andExpect(jsonPath("$[1].overallRating", is(34.4)))
                .andReturn();

        //Deserialize the response
        List<Subject> response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Subject>>() {
        });

        //Assert the response
        Assertions.assertEquals(2, response.size(), "Two Subjects should be returned");
    }

    @Test
    @DisplayName("Get Rating for Subject")
    void testGetSubjectRating() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/rating/subject/{subjectName}", "Computing Techniques"))
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
                .andExpect(jsonPath("$[2].overallRating", is(4.0)))
                .andReturn();

        //Deserialize the response
        List<Student> response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Student>>() {
        });

        //Assert the response
        Assertions.assertEquals(3, response.size(), "Three Student Records should be returned");
    }
}
