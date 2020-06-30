package com.capstone.studentrating.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subject {

    private String subjectName;

    private Double testScore;

    private Double quizScore;

    private Double labScore;

    private Double projectScore;

    private Double overallRating;
}