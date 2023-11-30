package com.ssafy.rentserver.repository;

import com.ssafy.rentserver.enums.SeatStatus;
import com.ssafy.rentserver.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SeatRepository extends JpaRepository<Seat, String> {

    Optional<Seat> findById(UUID seatId);

    @Query(value = "SELECT * FROM seat WHERE clazz_number = ?1 AND grade = ?2 AND school = ?3 AND seat_status NOT IN ('DELETED')", nativeQuery = true)
    Optional<List<Seat>> getAllSeats(int clazzNumber, int grade, String school);


}
