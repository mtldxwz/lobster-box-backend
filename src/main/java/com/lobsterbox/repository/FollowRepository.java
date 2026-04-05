package com.lobsterbox.repository;

import com.lobsterbox.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    
    // 检查是否已关注
    Optional<Follow> findByFollowerIdAndFollowingId(Long followerId, Long followingId);
    
    // 获取粉丝列表（谁关注了我）
    List<Follow> findByFollowingIdOrderByCreatedAtDesc(Long followingId);
    
    // 获取关注列表（我关注了谁）
    List<Follow> findByFollowerIdOrderByCreatedAtDesc(Long followerId);
    
    // 统计粉丝数
    long countByFollowingId(Long followingId);
    
    // 统计关注数
    long countByFollowerId(Long followerId);
    
    // 检查是否关注
    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);
}
