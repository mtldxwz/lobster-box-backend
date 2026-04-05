package com.lobsterbox.repository;

import com.lobsterbox.entity.UserCostume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCostumeRepository extends JpaRepository<UserCostume, Long> {
    List<UserCostume> findByUserIdOrderByObtainedAtDesc(Long userId);
    List<UserCostume> findByUserIdAndRarity(Long userId, String rarity);
    long countByUserId(Long userId);
    long countByUserIdAndRarity(Long userId, String rarity);
}