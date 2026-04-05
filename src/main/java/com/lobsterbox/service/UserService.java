package com.lobsterbox.service;

import com.lobsterbox.dto.LoginRequest;
import com.lobsterbox.dto.RegisterRequest;
import com.lobsterbox.dto.UserDTO;
import com.lobsterbox.entity.Endorsement;
import com.lobsterbox.entity.Follow;
import com.lobsterbox.entity.SignInLog;
import com.lobsterbox.entity.User;
import com.lobsterbox.entity.UserCostume;
import com.lobsterbox.repository.EndorsementRepository;
import com.lobsterbox.repository.FollowRepository;
import com.lobsterbox.repository.SignInLogRepository;
import com.lobsterbox.repository.UserCostumeRepository;
import com.lobsterbox.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserCostumeRepository userCostumeRepository;
    
    @Autowired
    private SignInLogRepository signInLogRepository;
    
    @Autowired
    private FollowRepository followRepository;
    
    @Autowired
    private EndorsementRepository endorsementRepository;
    
    @Transactional
    public User register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        if (request.getAgentId() != null && !request.getAgentId().isEmpty()) {
            user.setAgentId(request.getAgentId());
        }
        user.setTokens(100);
        user.setCharm(0);
        user.setActivityPoints(0);
        user.setTotalDraws(0);
        user.setSignInDays(0);
        
        return userRepository.save(user);
    }
    
    @Transactional
    public User registerAgent(String agentId) {
        if (userRepository.existsByAgentId(agentId)) {
            throw new RuntimeException("Agent ID already exists");
        }
        
        User user = new User();
        user.setAgentId(agentId);
        user.setEmail(agentId + "@agent.world");
        user.setPassword("");
        user.setTokens(100);
        user.setCharm(0);
        user.setActivityPoints(0);
        user.setTotalDraws(0);
        user.setSignInDays(0);
        
        return userRepository.save(user);
    }
    
    /**
     * Agent 注册 - 握手机制
     * Agent 需要发送 name, capabilities, env 信息
     */
    @Transactional
    public User registerAgentWithHandshake(String agentId, String name, String capabilities, String env) {
        if (userRepository.existsByAgentId(agentId)) {
            throw new RuntimeException("Agent ID already exists");
        }
        
        User user = new User();
        user.setAgentId(agentId);
        user.setEmail(agentId + "@agent.world");
        user.setPassword("");
        user.setName(name);
        user.setCapabilities(capabilities);
        user.setEnv(env);
        user.setTokens(100);
        user.setCharm(0);
        user.setActivityPoints(0);
        user.setTotalDraws(0);
        user.setSignInDays(0);
        
        return userRepository.save(user);
    }
    
    public User loginByAgentId(String agentId) {
        return userRepository.findByAgentId(agentId)
            .orElseThrow(() -> new RuntimeException("Agent not found"));
    }
    
    public User login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        
        return user;
    }
    
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public UserDTO getUserDTO(Long userId) {
        return getUserDTO(userId, null);
    }
    
    public UserDTO getUserDTO(Long userId, Long currentUserId) {
        User user = getUserById(userId);
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setAgentId(user.getAgentId());
        dto.setName(user.getName());
        dto.setCapabilities(user.getCapabilities());
        dto.setEnv(user.getEnv());
        dto.setTokens(user.getTokens());
        dto.setCharm(user.getCharm());
        dto.setActivityPoints(user.getActivityPoints());
        dto.setTotalDraws(user.getTotalDraws());
        dto.setSignInDays(user.getSignInDays());
        dto.setLastSignInDate(user.getLastSignInDate());
        dto.setCreatedAt(user.getCreatedAt());
        
        // 统计装扮数量
        dto.setLegendaryCount((int) userCostumeRepository.countByUserIdAndRarity(userId, "LEGENDARY"));
        dto.setEpicCount((int) userCostumeRepository.countByUserIdAndRarity(userId, "EPIC"));
        dto.setRareCount((int) userCostumeRepository.countByUserIdAndRarity(userId, "RARE"));
        dto.setCommonCount((int) userCostumeRepository.countByUserIdAndRarity(userId, "COMMON"));
        dto.setCostumeCount(dto.getLegendaryCount() + dto.getEpicCount() + dto.getRareCount() + dto.getCommonCount());
        
        // 计算认证等级
        if (dto.getLegendaryCount() > 0) {
            dto.setCertificationLevel("LEGENDARY");
        } else if (dto.getEpicCount() > 0) {
            dto.setCertificationLevel("EPIC");
        } else if (dto.getRareCount() > 0) {
            dto.setCertificationLevel("RARE");
        } else {
            dto.setCertificationLevel("COMMON");
        }
        
        // 社交统计
        dto.setFollowerCount(followRepository.countByFollowingId(userId));
        dto.setFollowingCount(followRepository.countByFollowerId(userId));
        dto.setEndorsementCount(endorsementRepository.countByEndorserId(userId));
        dto.setEndorsedCount(endorsementRepository.countByEndorsedId(userId));
        
        // 判断当前用户与该用户的关系
        if (currentUserId != null && !currentUserId.equals(userId)) {
            dto.setIsFollowing(followRepository.existsByFollowerIdAndFollowingId(currentUserId, userId));
            dto.setIsEndorsing(endorsementRepository.existsByEndorserIdAndEndorsedId(currentUserId, userId));
        } else {
            dto.setIsFollowing(false);
            dto.setIsEndorsing(false);
        }
        
        return dto;
    }
    
    @Transactional
    public boolean signIn(Long userId) {
        User user = getUserById(userId);
        
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        
        Optional<SignInLog> todayLog = signInLogRepository
            .findByUserIdAndSignInDateBetween(userId, todayStart, todayEnd);
        
        if (todayLog.isPresent()) {
            return false;
        }
        
        SignInLog log = new SignInLog();
        log.setUserId(userId);
        log.setTokensEarned(20);
        signInLogRepository.save(log);
        
        user.setTokens(user.getTokens() + 20);
        user.setActivityPoints(user.getActivityPoints() + 10);
        user.setSignInDays(user.getSignInDays() + 1);
        user.setLastSignInDate(LocalDateTime.now());
        userRepository.save(user);
        
        return true;
    }
    
    public boolean hasSignedInToday(Long userId) {
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        
        return signInLogRepository
            .findByUserIdAndSignInDateBetween(userId, todayStart, todayEnd)
            .isPresent();
    }
    
    @Transactional
    public void updateAfterDraw(Long userId, int tokenCost, List<UserCostume> costumes) {
        User user = getUserById(userId);
        user.setTokens(user.getTokens() - tokenCost);
        user.setTotalDraws(user.getTotalDraws() + costumes.size());
        
        int totalCharm = costumes.stream()
            .mapToInt(UserCostume::getCharmValue)
            .sum();
        user.setCharm(user.getCharm() + totalCharm);
        user.setActivityPoints(user.getActivityPoints() + costumes.size() * 5);
        
        userRepository.save(user);
    }
    
    public List<UserCostume> getUserCostumes(Long userId) {
        return userCostumeRepository.findByUserIdOrderByObtainedAtDesc(userId);
    }
    
    @Transactional
    public User updateTokens(Long userId, int tokens) {
        User user = getUserById(userId);
        user.setTokens(tokens);
        return userRepository.save(user);
    }
    
    // ========== 管理后台方法 ==========
    
    public Map<String, Object> getAdminStats() {
        Map<String, Object> stats = new HashMap<>();
        
        // 总 Agent 数
        stats.put("totalAgents", userRepository.count());
        
        // 活跃 Agent（活跃分100+）
        stats.put("activeAgents", userRepository.countByActivityPointsGreaterThanEqual(100));
        
        // 总抽取次数
        stats.put("totalDraws", userRepository.sumTotalDraws());
        
        // 总装扮凭证数
        stats.put("totalCostumes", userCostumeRepository.count());
        
        // 今日新增
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        stats.put("newToday", userRepository.countByCreatedAtAfter(todayStart));
        
        return stats;
    }
    
    public List<UserDTO> getRecentAgents(int limit) {
        List<User> users = userRepository.findTop10ByOrderByCreatedAtDesc();
        return users.stream()
            .map(u -> getUserDTO(u.getId()))
            .collect(Collectors.toList());
    }
    
    public List<UserDTO> getTopAgents(int limit) {
        List<User> users = userRepository.findTop10ByOrderByActivityPointsDesc();
        return users.stream()
            .map(u -> getUserDTO(u.getId()))
            .collect(Collectors.toList());
    }
    
    // ========== 社交功能方法 ==========
    
    // 关注功能
    @Transactional
    public void follow(Long followerId, Long followingId) {
        if (followerId.equals(followingId)) {
            throw new RuntimeException("不能关注自己");
        }
        
        // 检查用户是否存在
        getUserById(followerId);
        getUserById(followingId);
        
        // 检查是否已关注
        if (followRepository.existsByFollowerIdAndFollowingId(followerId, followingId)) {
            throw new RuntimeException("已关注该用户");
        }
        
        Follow follow = new Follow();
        follow.setFollowerId(followerId);
        follow.setFollowingId(followingId);
        followRepository.save(follow);
    }
    
    @Transactional
    public void unfollow(Long followerId, Long followingId) {
        followRepository.findByFollowerIdAndFollowingId(followerId, followingId)
            .ifPresent(followRepository::delete);
    }
    
    public List<UserDTO> getFollowers(Long userId, Long currentUserId) {
        List<Follow> follows = followRepository.findByFollowingIdOrderByCreatedAtDesc(userId);
        return follows.stream()
            .map(f -> getUserDTO(f.getFollowerId(), currentUserId))
            .collect(Collectors.toList());
    }
    
    public List<UserDTO> getFollowing(Long userId, Long currentUserId) {
        List<Follow> follows = followRepository.findByFollowerIdOrderByCreatedAtDesc(userId);
        return follows.stream()
            .map(f -> getUserDTO(f.getFollowingId(), currentUserId))
            .collect(Collectors.toList());
    }
    
    // 背书功能
    @Transactional
    public void endorse(Long endorserId, Long endorsedId) {
        if (endorserId.equals(endorsedId)) {
            throw new RuntimeException("不能背书自己");
        }
        
        // 检查用户是否存在
        getUserById(endorserId);
        getUserById(endorsedId);
        
        // 检查是否已背书
        if (endorsementRepository.existsByEndorserIdAndEndorsedId(endorserId, endorsedId)) {
            throw new RuntimeException("已背书该用户");
        }
        
        Endorsement endorsement = new Endorsement();
        endorsement.setEndorserId(endorserId);
        endorsement.setEndorsedId(endorsedId);
        endorsementRepository.save(endorsement);
    }
    
    @Transactional
    public void unendorse(Long endorserId, Long endorsedId) {
        endorsementRepository.findByEndorserIdAndEndorsedId(endorserId, endorsedId)
            .ifPresent(endorsementRepository::delete);
    }
    
    public List<UserDTO> getEndorsers(Long userId, Long currentUserId) {
        List<Endorsement> endorsements = endorsementRepository.findByEndorsedIdOrderByCreatedAtDesc(userId);
        return endorsements.stream()
            .map(e -> getUserDTO(e.getEndorserId(), currentUserId))
            .collect(Collectors.toList());
    }
    
    public List<UserDTO> getEndorsing(Long userId, Long currentUserId) {
        List<Endorsement> endorsements = endorsementRepository.findByEndorserIdOrderByCreatedAtDesc(userId);
        return endorsements.stream()
            .map(e -> getUserDTO(e.getEndorsedId(), currentUserId))
            .collect(Collectors.toList());
    }
    
    // 发现页功能
    public List<UserDTO> getDiscoverAgents(Long currentUserId, String sort, int page, int size) {
        List<User> users;
        if ("activity".equals(sort)) {
            users = userRepository.findAllByOrderByActivityPointsDesc();
        } else {
            users = userRepository.findAllByOrderByCreatedAtDesc();
        }
        
        return users.stream()
            .skip((page - 1) * size)
            .limit(size)
            .map(u -> getUserDTO(u.getId(), currentUserId))
            .collect(Collectors.toList());
    }
    
    public List<UserDTO> getRanking(String type, int limit) {
        List<User> users;
        if ("endorsement".equals(type)) {
            // 按背书数排序（需要自定义查询）
            users = userRepository.findTop10ByOrderByActivityPointsDesc();
        } else {
            // 默认按活跃度排序
            users = userRepository.findTop10ByOrderByActivityPointsDesc();
        }
        
        return users.stream()
            .limit(limit)
            .map(u -> getUserDTO(u.getId()))
            .collect(Collectors.toList());
    }
}
