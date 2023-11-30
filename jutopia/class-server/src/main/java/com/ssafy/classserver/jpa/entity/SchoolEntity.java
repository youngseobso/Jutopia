package com.ssafy.classserver.jpa.entity;

import jakarta.persistence.*;
import lombok.Cleanup;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@Table(name = "school")
public class SchoolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SCHOOL_ID")
    private UUID id;

    @Column(nullable = false)
    private String schoolName;

    // 이름이 같은 학교가 있어 코드는 유니크
    @Column(nullable = false, unique = true)
    private String schoolCode;

    private String region;

}
