package com.ssafy.memberserver.domain.pointtransaction.service;

import com.ssafy.memberserver.domain.account.entity.Account;
import com.ssafy.memberserver.domain.account.repository.AccountRepository;
import com.ssafy.memberserver.domain.pointtransaction.dto.request.PointExpenseRequest;
import com.ssafy.memberserver.domain.pointtransaction.dto.request.PointIncomeRequest;
import com.ssafy.memberserver.domain.pointtransaction.dto.response.PointExpenseResponse;
import com.ssafy.memberserver.domain.pointtransaction.dto.response.PointIncomeResponse;
import com.ssafy.memberserver.domain.pointtransaction.entity.PointTransaction;
import com.ssafy.memberserver.domain.pointtransaction.repository.PointTransactionRepository;
import com.ssafy.memberserver.domain.students.entity.Student;
import com.ssafy.memberserver.domain.students.repository.StudentRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class PointTransactionService {
    private final PointTransactionRepository pointTransactionRepository;
    private final StudentRepository studentRepository;
    private final AccountRepository accountRepository;

    @Operation(summary = "특정 학생의 포인트 조회")
    @Transactional(readOnly = true)
    public BigDecimal getStudentPoint(String studentId) {
        return studentRepository.findByStudentId(studentId)
                .map(Student::getPoint)
                .orElseThrow(() -> new NoSuchElementException("학생의 포인트를 찾을 수 없습니다."));
    }
    @Transactional
    public PointIncomeResponse pointIncome(PointIncomeRequest pointIncomeRequest){
        return studentRepository.findByStudentId(pointIncomeRequest.getStudentId())
                .map(student -> {
                    if (student.getPoint() != null && pointIncomeRequest.getIncome() != null) {
                        BigDecimal pointIncome = student.getPoint().add(pointIncomeRequest.getIncome());
                        log.info("{}",pointIncome);
                        student.pointIncomeUpdate(pointIncomeRequest,pointIncome);

                        Optional<Account> account = accountRepository.findAccountByStudentId(pointIncomeRequest.getStudentId());
                        BigDecimal accountBalance = account.get().getAccountBalance().subtract(pointIncomeRequest.getIncome());
                        account.get().updateBalance(accountBalance);
                        PointTransaction point = pointTransactionRepository.save(PointTransaction.incomeFrom(pointIncomeRequest, student));
                        pointTransactionRepository.flush();
                        return PointIncomeResponse.from(point);
                    } else {
                        throw new IllegalArgumentException("유효하지 않은 포인트나 수입입니다.");
                    }
                })
                .orElseThrow(() -> new NoSuchElementException("학생이 존재하지 않습니다."));
    }
    @Transactional
    public PointExpenseResponse pointExpense(PointExpenseRequest pointExpenseRequest){
        return studentRepository.findByStudentId(pointExpenseRequest.getStudentId())
                .map(student -> {
                    if (student.getPoint() != null && pointExpenseRequest.getExpense() != null) {
                        BigDecimal pointExpense = student.getPoint().subtract(pointExpenseRequest.getExpense());
                        student.pointExpenseUpdate(pointExpenseRequest, pointExpense);
                        PointTransaction point = pointTransactionRepository.save(PointTransaction.expenseFrom(pointExpenseRequest, student));

                        Optional<Account> account = accountRepository.findAccountByStudentId(pointExpenseRequest.getStudentId());
                        BigDecimal accountBalance = account.get().getAccountBalance().add(pointExpenseRequest.getExpense());
                        account.get().updateBalance2(accountBalance);
                        pointTransactionRepository.flush();
                        return PointExpenseResponse.from(point);
                    } else {
                        throw new IllegalArgumentException("유효하지 않은 포인트나 지출입니다.");
                    }
                })
                .orElseThrow(() -> new NoSuchElementException("학생이 존재하지 않습니다."));
    }
}
