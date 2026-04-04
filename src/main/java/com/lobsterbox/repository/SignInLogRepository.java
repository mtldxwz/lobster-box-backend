package com.lobsterbox.repository;

import com.lobsterbox.entity.SignInLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SignInLogRepository extends JpaRepository<SignInLog, Long> {
    Optional<SignInLog> findByUserIdAndSignInDateBetween(Long userId, LocalDateTime start, LocalDateTime end);
    long countByUserId(Long userId);
}