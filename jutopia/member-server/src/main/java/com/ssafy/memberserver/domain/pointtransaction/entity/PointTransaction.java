package com.ssafy.memberserver.domain.pointtransaction.entity;

import com.ssafy.memberserver.common.enums.HistoryType;
import com.ssafy.memberserver.domain.pointtransaction.dto.request.PointExpenseRequest;
import com.ssafy.memberserver.domain.pointtransaction.dto.request.PointIncomeRequest;
import com.ssafy.memberserver.domain.students.entity.Student;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Entity
@Getter
public class PointTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String place;
    private BigDecimal income;
    private BigDecimal expense;
    private HistoryType historyType;
    @ManyToOne
    private Student student;

    public static PointTransaction incomeFrom(PointIncomeRequest pointIncomeRequest, Student student){
        return PointTransaction.builder()
                .income(pointIncomeRequest.getIncome())
                .place(pointIncomeRequest.getPlace())
                .student(student)
                .build();
    }
    public static PointTransaction expenseFrom(PointExpenseRequest pointExpenseRequest, Student student){
        return PointTransaction.builder()
                .expense(pointExpenseRequest.getExpense())
                .place(pointExpenseRequest.getPlace())
                .student(student)
                .build();
    }
}
