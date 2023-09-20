package com.ssafy.bankserver.domain.account.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "bank")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
