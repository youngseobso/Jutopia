package com.ssafy.rentserver.repository;

import com.ssafy.rentserver.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SeatRepository extends JpaRepository<Seat, String> {

    Optional<Seat> findById(UUID seatId);

}
