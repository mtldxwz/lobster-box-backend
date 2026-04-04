package com.lobsterbox.service;

import com.lobsterbox.dto.LoginRequest;
import com.lobsterbox.dto.RegisterRequest;
import com.lobsterbox.dto.UserDTO;
import com.lobsterbox.entity.SignInLog;
import com.lobsterbox.entity.User;
import com.lobsterbox.entity.UserCostume;
import com.lobsterbox.repository.SignInLogRepository;
import com.lobsterbox.repository.UserCostumeRepository;
import com.lobsterbox.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserCostumeRepository userCostumeRepository;
    
    @Autowired
    private SignInLogRepository signInLogRepository;
    
    @Transactional
    public User register(RegisterRequest request) {
        // 检查邮箱是否已存在
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // 实际应该加密
        if (request.getAgentId() != null && !request.getAgentId().isEmpty()) {
            user.setAgentId(request.getAgentId());
        }
        user.setTokens(100); // 初始 Token
        user.setCharm(0);
        user.setActivityPoints(0);
        user.setTotalDraws(0);
        user.setSignInDays(0);
        
        return userRepository.save(user);
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
        User user = getUserById(userId);
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setAgentId(user.getAgentId());
        dto.setTokens(user.getTokens());
        dto.setCharm(user.getCharm());
        dto.setActivityPoints(user.getActivityPoints());
        dto.setTotalDraws(user.getTotalDraws());
        dto.setSignInDays(user.getSignInDays());
        dto.setLastSignInDate(user.getLastSignInDate());
        
        // 统计装扮数量
        dto.setLegendaryCount((int) userCostumeRepository.countByUserIdAndRarity(userId, "LEGENDARY"));
        dto.setEpicCount((int) userCostumeRepository.countByUserIdAndRarity(userId, "EPIC"));
        dto.setRareCount((int) userCostumeRepository.countByUserIdAndRarity(userId, "RARE"));
        dto.setCommonCount((int) userCostumeRepository.countByUserIdAndRarity(userId, "COMMON"));
        
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
        
        return dto;
    }
    
    @Transactional
    public boolean signIn(Long userId) {
        User user = getUserById(userId);
        
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        
        // 检查今天是否已签到
        Optional<SignInLog> todayLog = signInLogRepository
            .findByUserIdAndSignInDateBetween(userId, todayStart, todayEnd);
        
        if (todayLog.isPresent()) {
            return false; // 今天已签到
        }
        
        // 记录签到
        SignInLog log = new SignInLog();
        log.setUserId(userId);
        log.setTokensEarned(20);
        signInLogRepository.save(log);
        
        // 更新用户
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
}