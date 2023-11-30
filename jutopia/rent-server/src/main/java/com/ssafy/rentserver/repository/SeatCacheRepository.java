package com.ssafy.rentserver.repository;

import com.ssafy.rentserver.dto.SeatResponse;
import com.ssafy.rentserver.model.Seat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SeatCacheRepository {

    private final RedisTemplate<String, Seat> seatRedisTemplate;
    private final RedisTemplate<String, List> seatsRedisTemplate;
    private final static Duration SEAT_CACHE_TTL = Duration.ofDays(3);


    public void setSeat(Seat seat) {
        String key = getKey(seat.getId().toString());
        log.info("Set Seat to Redis {}:{}", key, seat);
        seatRedisTemplate.opsForValue().set(key, seat, SEAT_CACHE_TTL);
    }

    public Seat getSeat(String seatId) {
        String key = getKey(seatId);
        Seat seat = seatRedisTemplate.opsForValue().get(key);
        log.info("Get data from Redis {}:{}", key, seat);
        return seat;
    }
    public void clearSeat(String seatId){
        String key = getKey(seatId);
        seatRedisTemplate.delete(key);
    }

    public void setSeats(List<SeatResponse> seats){
        SeatResponse seat = seats.get(0);
        String key = getListKey(seat.getSchool(), seat.getGrade(), seat.getClazzNumber());
        seatsRedisTemplate.opsForValue().set(key, seats);
        seatRedisTemplate.expire(key, SEAT_CACHE_TTL);
    }

    public List<SeatResponse> getSeats(String key) {
        List<SeatResponse> seats = seatsRedisTemplate.opsForValue().get(key);
        return seats;
    }

    public void clearSeats(String key) {
        seatsRedisTemplate.delete(key);
    }

    private String getKey(String seatId) {
        return "SEAT:" + seatId;
    }

    public String getListKey(String school, int grade, int clazzNumber){
        return "seats:" + school + ":" + grade + ":" + clazzNumber;
    }

}
