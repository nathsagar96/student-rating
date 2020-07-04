package com.capstone.studentrating.service;

import com.capstone.studentrating.dto.Student;
import com.capstone.studentrating.dto.Subject;
import com.capstone.studentrating.model.Assignment;
import com.capstone.studentrating.model.Distribution;
import com.capstone.studentrating.repository.AssignmentRepository;
import com.capstone.studentrating.repository.DistributionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentRatingService {

    final AssignmentRepository assignmentRepository;

    final DistributionRepository distributionRepository;

    public StudentRatingService(AssignmentRepository assignmentRepository, DistributionRepository distributionRepository) {
        this.assignmentRepository = assignmentRepository;
        this.distributionRepository = distributionRepository;
    }

    public List<Subject> calculateStudentRating(String studentName) {
        List<Subject> rating = new ArrayList<>();
        List<Assignment> assignments = assignmentRepository.findByStudentNameIgnoreCase(studentName);
        List<Distribution> distributionList = distributionRepository.findAll();
        Map<String, Double> distributions = distributionList.stream()
                .collect(Collectors.toMap(Distribution::getAssignmentCategory, Distribution::getWeight));

        //get all unique subjects
        List<String> uniqueSubjects = assignments.stream()
                .map(Assignment::getSubject)
                .distinct()
                .collect(Collectors.toList());

        //calculate rating for each subject
        for (String subjectName : uniqueSubjects) {
            Subject subject = new Subject();
            //Set Subject Name
            subject.setSubjectName(subjectName);
            List<Double> testScores = new ArrayList<>();
            List<Double> quizScores = new ArrayList<>();
            List<Double> labScores = new ArrayList<>();
            List<Double> projectScores = new ArrayList<>();

            for (Assignment assignment : assignments) {
                //separate scores for different category of assignments
                if (assignment.getSubject().equalsIgnoreCase(subjectName)) {
                    if (assignment.getAssignmentCategory().matches("test(.*)"))
                        testScores.add(assignment.getPoints());
                    else if (assignment.getAssignmentCategory().matches("quiz(.*)"))
                        quizScores.add(assignment.getPoints());
                    else if (assignment.getAssignmentCategory().matches("lab(.*)"))
                        labScores.add(assignment.getPoints());
                    else if (assignment.getAssignmentCategory().matches("project(.*)"))
                        projectScores.add(assignment.getPoints());
                }
            }
            //Calculate Scores for each assignment Category
            subject.setTestScore(calculateScore(testScores, distributions.get("test")));
            subject.setQuizScore(calculateScore(quizScores, distributions.get("quiz")));
            subject.setLabScore(calculateScore(labScores, distributions.get("lab")));
            subject.setProjectScore(calculateScore(projectScores, distributions.get("project")));

            //Set Overall Rating
            subject.setOverallRating(subject.getTestScore() + subject.getQuizScore() + subject.getLabScore() + subject.getProjectScore());
            rating.add(subject);
        }
        return rating;
    }

    public List<Student> calculateSubjectRating(String subject) {
        List<Student> rating = new ArrayList<>();
        List<Assignment> assignments = assignmentRepository.findBySubjectIgnoreCase(subject);
        List<Distribution> distributionList = distributionRepository.findAll();
        Map<String, Double> distributions = distributionList.stream()
                .collect(Collectors.toMap(Distribution::getAssignmentCategory, Distribution::getWeight));

        //Get all unique student names
        List<String> uniqueStudents = assignments.stream()
                .map(Assignment::getStudentName)
                .distinct()
                .collect(Collectors.toList());

        //calculate rating for each student
        for (String studentName : uniqueStudents) {
            Student student = new Student();
            student.setStudentName(studentName);
            List<Double> testScores = new ArrayList<>();
            List<Double> quizScores = new ArrayList<>();
            List<Double> labScores = new ArrayList<>();
            List<Double> projectScores = new ArrayList<>();

            for (Assignment assignment : assignments) {
                //separate scores for different category of assignments
                if (assignment.getStudentName().equalsIgnoreCase(studentName)) {
                    if (assignment.getAssignmentCategory().matches("test(.*)"))
                        testScores.add(assignment.getPoints());
                    else if (assignment.getAssignmentCategory().matches("quiz(.*)"))
                        quizScores.add(assignment.getPoints());
                    else if (assignment.getAssignmentCategory().matches("lab(.*)"))
                        labScores.add(assignment.getPoints());
                    else if (assignment.getAssignmentCategory().matches("project(.*)"))
                        projectScores.add(assignment.getPoints());
                }
            }
            //Calculate Scores for each assignment Category
            student.setTestScore(calculateScore(testScores, distributions.get("test")));
            student.setQuizScore(calculateScore(quizScores, distributions.get("quiz")));
            student.setLabScore(calculateScore(labScores, distributions.get("lab")));
            student.setProjectScore(calculateScore(projectScores, distributions.get("project")));

            //Set Overall Rating
            student.setOverallRating(student.getTestScore() + student.getQuizScore() + student.getLabScore() + student.getProjectScore());
            rating.add(student);
        }
        return rating;
    }

    Double calculateScore(List<Double> scores, Double weight) {
        Double totalScore = 0.0;
        int size = scores.size();
        if (size != 0) {
            for (Double score : scores)
                totalScore = totalScore + (weight / size) * score;
        }
        return totalScore / 100;
    }
}
