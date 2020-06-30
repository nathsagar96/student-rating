package com.capstone.studentrating.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "distributions")
public class Distribution implements Serializable {

    @Id
    private Integer categoryId;

    @Column(unique = true)
    private String assignmentCategory;

    private Double weight;
}
