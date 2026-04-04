package com.lobsterbox.repository;

import com.lobsterbox.entity.UserCostume;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserCostumeRepository extends JpaRepository<UserCostume, Long> {
    List<UserCostume> findByUserId(Long userId);
    long countByUserId(Long userId);
}
