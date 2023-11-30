package com.ssafy.classserver.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@Table(name = "grade")
public class GradeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "GRADE_ID")
    private UUID id;

    @Column(nullable = false)
    private int gradeNum;
    private BigDecimal gradeAccountPoint;
    private BigDecimal gradeAccountInitPoint;

    // 어느 학교의 반인지
    @ManyToOne
    @JoinColumn(name = "SCHOOL_ID")
    private SchoolEntity school;


}
