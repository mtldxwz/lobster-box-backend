package com.lobsterbox.service;

import com.lobsterbox.dto.CostumeDTO;
import com.lobsterbox.dto.DrawResult;
import com.lobsterbox.entity.Costume;
import com.lobsterbox.entity.User;
import com.lobsterbox.entity.UserCostume;
import com.lobsterbox.repository.CostumeRepository;
import com.lobsterbox.repository.UserCostumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class BoxService {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CostumeRepository costumeRepository;
    
    @Autowired
    private UserCostumeRepository userCostumeRepository;
    
    private final Random random = new Random();
    
    @Transactional
    public DrawResult draw(Long userId, int count) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        int cost = count == 1 ? 10 : 88;
        if (user.getToken() < cost) {
            throw new RuntimeException("Token不足");
        }
        
        user = userService.updateToken(userId, -cost);
        
        List<CostumeDTO> results = new ArrayList<>();
        
        for (int i = 0; i < count; i++) {
            Costume costume = drawOneCostume(user);
            CostumeDTO dto = convertToDTO(costume);
            results.add(dto);
        }
        
        DrawResult drawResult = new DrawResult();
        drawResult.setCostumes(results);
        drawResult.setNewToken(user.getToken());
        drawResult.setPityCount(user.getPityCount());
        
        return drawResult;
    }
    
    private Costume drawOneCostume(User user) {
        Costume.Rarity rarity;
        
        if (user.getPityCount() >= 89) {
            rarity = Costume.Rarity.LEGENDARY;
            userService.resetPityCount(user.getId());
        } else {
            double rand = random.nextDouble();
            
            if (rand < 0.02) {
                rarity = Costume.Rarity.LEGENDARY;
                userService.resetPityCount(user.getId());
            } else if (rand < 0.10) {
                rarity = Costume.Rarity.EPIC;
                userService.incrementPityCount(user.getId());
            } else if (rand < 0.40) {
                rarity = Costume.Rarity.RARE;
                userService.incrementPityCount(user.getId());
            } else {
                rarity = Costume.Rarity.COMMON;
                userService.incrementPityCount(user.getId());
            }
        }
        
        List<Costume> costumes = costumeRepository.findByRarity(rarity);
        if (costumes.isEmpty()) {
            Costume defaultCostume = createDefaultCostume(rarity);
            costumes.add(costumeRepository.save(defaultCostume));
        }
        
        Costume costume = costumes.get(random.nextInt(costumes.size()));
        
        UserCostume userCostume = new UserCostume();
        userCostume.setUser(user);
        userCostume.setCostume(costume);
        userCostume.setUniqueId("LB" + System.currentTimeMillis() + random.nextInt(1000));
        userCostumeRepository.save(userCostume);
        
        return costume;
    }
    
    private Costume createDefaultCostume(Costume.Rarity rarity) {
        Costume costume = new Costume();
        costume.setRarity(rarity);
        
        switch (rarity) {
            case LEGENDARY:
                costume.setName("龙虾帝王");
                costume.setImage("legendary_lobster.png");
                costume.setStyle("传说");
                break;
            case EPIC:
                costume.setName("龙虾将军");
                costume.setImage("epic_lobster.png");
                costume.setStyle("史诗");
                break;
            case RARE:
                costume.setName("龙虾剑客");
                costume.setImage("rare_lobster.png");
                costume.setStyle("武侠");
                break;
            default:
                costume.setName("龙虾小兵");
                costume.setImage("common_lobster.png");
                costume.setStyle("普通");
        }
        
        return costume;
    }
    
    private CostumeDTO convertToDTO(Costume costume) {
        CostumeDTO dto = new CostumeDTO();
        dto.setId(costume.getId());
        dto.setName(costume.getName());
        dto.setImage(costume.getImage());
        dto.setRarity(costume.getRarity().name());
        dto.setRarityText(getRarityText(costume.getRarity()));
        dto.setStyle(costume.getStyle());
        return dto;
    }
    
    private String getRarityText(Costume.Rarity rarity) {
        switch (rarity) {
            case LEGENDARY: return "传说";
            case EPIC: return "史诗";
            case RARE: return "稀有";
            default: return "普通";
        }
    }
}
