package com.ssafy.rentserver.service;

import com.ssafy.common.api.Api;
import com.ssafy.common.error.ErrorCode;
import com.ssafy.common.error.RentErrorCode;
import com.ssafy.rentserver.dto.PointReductionRequest;
import com.ssafy.rentserver.dto.SeatChangeRequest;
import com.ssafy.rentserver.dto.SeatResponse;
import com.ssafy.rentserver.enums.SeatStatus;
import com.ssafy.rentserver.feignclient.UserServerClient;
import com.ssafy.rentserver.model.Seat;
import com.ssafy.rentserver.repository.SeatCacheRepository;
import com.ssafy.rentserver.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatService {


    private final SeatRepository seatRepository;
    private final SeatCacheRepository seatCacheRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final UserServerClient userServerClient;

    public List<Seat> createGrid(int totalCount, int clazzNumber, int grade, String school) {
        Optional<List<Seat>> existingSeats = seatRepository.getAllSeats(clazzNumber, grade, school);
        if (existingSeats.isPresent()){
            existingSeats.get().forEach(it -> it.changeStatus(SeatStatus.DELETED));
            String key = seatCacheRepository.getListKey(school, grade, clazzNumber);
            seatCacheRepository.clearSeats(key);
        }

        List<Seat> seats = new ArrayList<>();
        int count = 1;
        for (int i = 0; i < totalCount; i++) {
            var seat = Seat.builder()
                    .price(new BigDecimal(100000))
                    .clazzNumber(clazzNumber)
                    .grade(grade)
                    .school(school)
                    .position(count)
                    .seatStatus(SeatStatus.AVAILABLE)
                    .build();
            seats.add(seat);
            count++;
        }
        return seatRepository.saveAll(seats);
    }

    public Api<?> changeSeatInfo(SeatChangeRequest request){
        Optional<Seat> seat = seatRepository.findById(request.getSeatId());
        if (seat.isEmpty()) {
            return Api.ERROR(RentErrorCode.BAD_REQUEST, "존재하지 않는 좌석입니다.");
        }
        var data = seat.get();
        data.changePrice(request.getPrice());
        data.changeUserId(request.getUserId());
        data.changeStatus(request.getSeatStatus());

        return Api.OK(data);
    }


    public Api<?> getAllSeat(int clazzNumber, int grade, String school) {
        String key = seatCacheRepository.getListKey(school, grade, clazzNumber);
        var seats = seatCacheRepository.getSeats(key);
        if (seats == null) {
            Optional<List<Seat>> seatList = seatRepository.getAllSeats(clazzNumber, grade, school);
            if (seatList.get().isEmpty()) {
                return Api.ERROR(RentErrorCode.BAD_REQUEST, "존재하지 않는 좌석 정보입니다.");
            }
            List<SeatResponse> response = seatList.get().stream().map(SeatResponse::toResponse).toList();
            seatCacheRepository.setSeats(response);
            return Api.OK(response);
        }

        return Api.OK(seats);
    }

    public Api<?> getSeatInfo(UUID seatId) {
        var seat = seatCacheRepository.getSeat(seatId.toString());
        if (seat == null) {
            Optional<Seat> getFromDb = seatRepository.findById(seatId);
            if (getFromDb.isEmpty()) {
                return Api.ERROR(RentErrorCode.BAD_REQUEST, "존재하지 않는 좌석입니다.");
            }
            seatCacheRepository.setSeat(getFromDb.get());
            return Api.OK(SeatResponse.toResponse(getFromDb.get()));
        }
        return Api.OK(SeatResponse.toResponse(seat));
    }


    public Api<?> requestSeat(String seatId, String userId) {
        //TODO: 기존 사용자와 동일한 사용자가 같은 자리를 신청한 것이라면 포인트를 1.5배 내도록 처리
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        Boolean lockAcquired = ops.setIfAbsent("lock:"+seatId,"locked",10, TimeUnit.SECONDS);
        if(Boolean.FALSE.equals(lockAcquired)) {
            return Api.ERROR(RentErrorCode.FAIL, "다른 사람이 먼저 신청헀습니다.");
        }

        try {
            var seat = seatCacheRepository.getSeat(seatId);
            if (seat.getSeatStatus() != SeatStatus.AVAILABLE) {
                return Api.ERROR(ErrorCode.BAD_REQUEST, "신청할 수 없는 좌석입니다.");
            }

            var request = PointReductionRequest.builder()
                    .studentId(userId)
                    .seatId(seatId)
                    .point(seat.getPrice())
                    .build();

            Api<?> pointResponse = userServerClient.reducePointAndSetSeat(request);

            var errorCode = pointResponse.getResult().getResultCode();

            if (errorCode == 1002) {
                return Api.ERROR(RentErrorCode.POINT_LACK, "포인트가 부족합니다.");
            }

            if (errorCode != 200) {
                return Api.ERROR(RentErrorCode.SERVER_ERROR, "멤버 서버에서 에러 발생");
            }

            seat.changeStatus(SeatStatus.INUSE);
            seat.changeUserId(userId);
            var newSeat = SeatResponse.toResponse(seatRepository.save(seat));

            String key = seatCacheRepository.getListKey(seat.getSchool(), seat.getGrade(), seat.getClazzNumber());
            seatCacheRepository.clearSeat(seatId);
            seatCacheRepository.clearSeats(key);

            return Api.OK(newSeat);
        } catch (Exception e){
            log.info("{}",e.toString());
            return Api.ERROR(RentErrorCode.SERVER_ERROR);
        }
        finally {
            redisTemplate.delete("lock:" + seatId);
        }
    }
}
