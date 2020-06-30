package com.capstone.studentrating.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "assignments")
public class Assignment implements Serializable {

    @Id
    private Integer assignmentId;

    private String studentName;

    private String subject;

    private String assignmentCategory;

    private LocalDate dateOfSubmission;

    private Double points;
}
