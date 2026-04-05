package com.lobsterbox.repository;

import com.lobsterbox.entity.Endorsement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EndorsementRepository extends JpaRepository<Endorsement, Long> {
    
    // 检查是否已背书
    Optional<Endorsement> findByEndorserIdAndEndorsedId(Long endorserId, Long endorsedId);
    
    // 获取谁背书了我
    List<Endorsement> findByEndorsedIdOrderByCreatedAtDesc(Long endorsedId);
    
    // 获取我背书了谁
    List<Endorsement> findByEndorserIdOrderByCreatedAtDesc(Long endorserId);
    
    // 统计被背书数
    long countByEndorsedId(Long endorsedId);
    
    // 统计背书数
    long countByEndorserId(Long endorserId);
    
    // 检查是否背书
    boolean existsByEndorserIdAndEndorsedId(Long endorserId, Long endorsedId);
}
