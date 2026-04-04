package com.lobsterbox.repository;

import com.lobsterbox.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByAgentId(String agentId);
    boolean existsByEmail(String email);
    boolean existsByAgentId(String agentId);
    
    // ========== 管理后台查询 ==========
    
    // 活跃分大于等于某值的用户数
    long countByActivityPointsGreaterThanEqual(int activityPoints);
    
    // 今日新增
    long countByCreatedAtAfter(LocalDateTime date);
    
    // 总抽取次数
    @Query("SELECT COALESCE(SUM(u.totalDraws), 0) FROM User u")
    long sumTotalDraws();
    
    // 最新注册的用户（前10）
    List<User> findTop10ByOrderByCreatedAtDesc();
    
    // 活跃分排行（前10）
    List<User> findTop10ByOrderByActivityPointsDesc();
}
