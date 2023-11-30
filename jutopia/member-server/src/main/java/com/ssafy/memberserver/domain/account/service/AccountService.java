package com.ssafy.memberserver.domain.account.service;

import com.ssafy.memberserver.common.enums.HistoryType;
import com.ssafy.memberserver.domain.account.dto.request.AccountDeleteRequest;
import com.ssafy.memberserver.domain.account.dto.request.SendMoneyRequest;
import com.ssafy.memberserver.domain.account.dto.response.*;
import com.ssafy.memberserver.domain.account.entity.Account;
import com.ssafy.memberserver.domain.account.repository.AccountRepository;
import com.ssafy.memberserver.domain.history.entity.History;
import com.ssafy.memberserver.domain.history.repository.HistoryRepository;
import com.ssafy.memberserver.domain.students.entity.Student;
import com.ssafy.memberserver.domain.students.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;
    private final StudentRepository studentRepository;
    private final HistoryRepository historyRepository;
    @Transactional
    public AccountInfoResponse getAccountInfo(String studentId) {
        Account account = accountRepository.findAccountByStudentId(studentId)
                .orElseThrow(() -> new NoSuchElementException("학생 계좌를 찾을 수 없습니다"));
        return AccountInfoResponse.from(account);
    }
    @Transactional
    public AccountDeleteResponse deleteAccount(AccountDeleteRequest accountDeleteRequest, UUID id){
        return accountRepository.findById(id)
                .map(account -> {
                    account.delete(accountDeleteRequest);
                    return AccountDeleteResponse.of(true);
                })
                .orElseThrow(()-> new NoSuchElementException("없는 계좌입니다"));
    }
    @Transactional
    public SendMoneyResponse sendMoney(SendMoneyRequest sendMoneyRequest){
        Account senderAccount = accountRepository.findAccountByStudentId(sendMoneyRequest.getSender())
                .orElseThrow(() -> new NoSuchElementException("보내는 사람을 찾을 수 없습니다."));
        // 돈을 보낼 때 보내는 양이 넘어 가면 안된다. //같은 반의 금액인지 확인을한다. 거래 시간을 찍는다.
        Account receiverAccount = accountRepository.findAccountByStudentId(sendMoneyRequest.getReceiver())
                .orElseThrow(() -> new NoSuchElementException("받는 사람을 찾을 수 없습니다"));
        BigDecimal amount = sendMoneyRequest.getAmount();
        log.info("amount:{}",amount);
        senderAccount.withdraw(amount);
        log.info("withdraw:{}",amount.getClass());
        receiverAccount.deposit(amount);
        accountRepository.flush();

        History senderHistory = new History();
        senderHistory.senderHistory(senderAccount,HistoryType.EXPENSE, sendMoneyRequest.getSender(), sendMoneyRequest.getReceiver(), amount, senderAccount.getAccountBalance());
        historyRepository.save(senderHistory);
        History receiverHistory = new History();
        receiverHistory.receiverHistory(receiverAccount,HistoryType.INCOME, sendMoneyRequest.getReceiver(), sendMoneyRequest.getSender(), amount, receiverAccount.getAccountBalance());
        historyRepository.save(receiverHistory);

        return new SendMoneyResponse("송금이 성공적으로 완료되었습니다.");
    }
    @Transactional(readOnly = true)
    public List<ClassRoomListResponse> classRoomList(UUID classroomId){
        List<Student> students = studentRepository.findAllByClassroomId(classroomId);
        return students.stream()
                .map(ClassRoomListResponse::from)
                .collect(Collectors.toList());
    }
}
