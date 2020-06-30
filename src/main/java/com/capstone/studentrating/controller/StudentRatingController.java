package com.capstone.studentrating.controller;

import com.capstone.studentrating.dto.Student;
import com.capstone.studentrating.dto.Subject;
import com.capstone.studentrating.service.StudentRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rating")
public class StudentRatingController {

    @Autowired
    StudentRatingService studentRatingService;

    @GetMapping("/student/{studentName}")
    public ResponseEntity<List<Subject>> getStudentRating(@PathVariable String studentName) {
        return ResponseEntity.ok(studentRatingService.calculateStudentRating(studentName));
    }

    @GetMapping("/subject/{subjectName}")
    public ResponseEntity<List<Student>> getSubjectRating(@PathVariable String subjectName) {
        return ResponseEntity.ok(studentRatingService.calculateSubjectRating(subjectName));
    }
}
