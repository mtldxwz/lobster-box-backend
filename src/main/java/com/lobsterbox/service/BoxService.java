package com.lobsterbox.service;

import com.lobsterbox.dto.UserDTO;
import com.lobsterbox.entity.Costume;
import com.lobsterbox.entity.UserCostume;
import com.lobsterbox.repository.CostumeRepository;
import com.lobsterbox.repository.UserCostumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BoxService {
    
    @Autowired
    private CostumeRepository costumeRepository;
    
    @Autowired
    private UserCostumeRepository userCostumeRepository;
    
    @Autowired
    private UserService userService;
    
    private static final Map<String, Double> RARITY_PROBABILITY = new HashMap<>();
    private static final Map<String, Integer> RARITY_CHARM = new HashMap<>();
    
    static {
        RARITY_PROBABILITY.put("LEGENDARY", 0.02);
        RARITY_PROBABILITY.put("EPIC", 0.08);
        RARITY_PROBABILITY.put("RARE", 0.30);
        RARITY_PROBABILITY.put("COMMON", 0.60);
        
        RARITY_CHARM.put("LEGENDARY", 100);
        RARITY_CHARM.put("EPIC", 50);
        RARITY_CHARM.put("RARE", 20);
        RARITY_CHARM.put("COMMON", 5);
    }
    
    @Transactional
    public List<UserCostume> draw(Long userId, int count) {
        // ========== 活跃分门槛判断 ==========
        UserDTO user = userService.getUserDTO(userId);
        int activityPoints = user.getActivityPoints() != null ? user.getActivityPoints() : 0;
        
        if (activityPoints < 100) {
            throw new RuntimeException("活跃分不足，需要100+才能抽盲盒。当前活跃分: " + activityPoints + "。请先签到积累活跃分。");
        }
        
        // ========== Token 判断 ==========
        int tokenCost = count == 1 ? 10 : 88;
        if (user.getTokens() < tokenCost) {
            throw new RuntimeException("Token不足，需要 " + tokenCost + " Token");
        }
        
        // ========== 抽取逻辑 ==========
        List<Costume> allCostumes = costumeRepository.findAll();
        
        if (allCostumes.isEmpty()) {
            throw new RuntimeException("No costumes available");
        }
        
        // 按稀有度分类
        Map<String, List<Costume>> costumesByRarity = new HashMap<>();
        for (Costume costume : allCostumes) {
            costumesByRarity.computeIfAbsent(costume.getRarity().name(), k -> new ArrayList<>()).add(costume);
        }
        
        List<UserCostume> results = new ArrayList<>();
        
        for (int i = 0; i < count; i++) {
            // 随机决定稀有度
            String rarity = determineRarity();
            
            // 从该稀有度的装扮中随机选择
            List<Costume> pool = costumesByRarity.get(rarity);
            if (pool == null || pool.isEmpty()) {
                pool = costumesByRarity.get("COMMON");
            }
            
            Costume costume = pool.get(new Random().nextInt(pool.size()));
            
            // 创建用户装扮
            UserCostume userCostume = new UserCostume();
            userCostume.setUserId(userId);
            userCostume.setCostumeId(costume.getId());
            userCostume.setCostumeName(costume.getName());
            userCostume.setCostumeImage(costume.getImage());
            userCostume.setRarity(costume.getRarity().name());
            userCostume.setSerialNumber(generateSerialNumber(costume.getName()));
            userCostume.setCharmValue(RARITY_CHARM.getOrDefault(costume.getRarity(), 5));
            
            userCostumeRepository.save(userCostume);
            results.add(userCostume);
        }
        
        // 更新用户统计
        userService.updateAfterDraw(userId, tokenCost, results);
        
        return results;
    }
    
    private String determineRarity() {
        double random = Math.random();
        
        if (random < 0.02) {
            return "LEGENDARY";
        } else if (random < 0.10) {
            return "EPIC";
        } else if (random < 0.40) {
            return "RARE";
        } else {
            return "COMMON";
        }
    }
    
    private int generateSerialNumber(String costumeName) {
        long count = userCostumeRepository.findAll().stream()
            .filter(uc -> uc.getCostumeName().equals(costumeName))
            .count();
        return (int) (count + 1);
    }
    
    public List<UserCostume> getUserCostumes(Long userId) {
        return userCostumeRepository.findByUserIdOrderByObtainedAtDesc(userId);
    }
}
