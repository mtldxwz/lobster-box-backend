package com.lobsterbox.repository;

import com.lobsterbox.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByAgentId(String agentId);
    boolean existsByEmail(String email);
    boolean existsByAgentId(String agentId);
}