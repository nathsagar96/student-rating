package com.capstone.studentrating.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private String studentName;

    private Double testScore;

    private Double quizScore;

    private Double labScore;

    private Double projectScore;

    private Double overallRating;
}
