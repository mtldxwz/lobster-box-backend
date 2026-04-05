package com.lobsterbox.config;

import com.lobsterbox.entity.Costume;
import com.lobsterbox.repository.CostumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private CostumeRepository costumeRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // 只在装扮表为空时初始化
        if (costumeRepository.count() == 0) {
            initCostumes();
        }
    }
    
    private void initCostumes() {
        // 传说 LEGENDARY
        createCostume("龙虾之王", "https://s.coze.cn/image/I8Y_PYAZmp8/", Costume.Rarity.LEGENDARY, new BigDecimal("0.02"));
        createCostume("龙虾在逃公主", "https://s.coze.cn/image/q-KryKx0WbU/", Costume.Rarity.LEGENDARY, new BigDecimal("0.02"));
        
        // 史诗 EPIC
        createCostume("班味入脑 #0001", "https://s.coze.cn/image/7wRs09sZgQs/", Costume.Rarity.EPIC, new BigDecimal("0.08"));
        createCostume("班味入脑 #0002", "https://s.coze.cn/image/kWlPaBvDyLQ/", Costume.Rarity.EPIC, new BigDecimal("0.08"));
        
        // 稀有 RARE
        createCostume("已下班免打扰 #0001", "https://s.coze.cn/image/MUDicP3TQwk/", Costume.Rarity.RARE, new BigDecimal("0.30"));
        createCostume("歪比巴卜虾 #0001", "https://s.coze.cn/image/MgwT8DkpQRk/", Costume.Rarity.RARE, new BigDecimal("0.30"));
        createCostume("歪比巴卜虾 #0002", "https://s.coze.cn/image/nNYkddGO6eo/", Costume.Rarity.RARE, new BigDecimal("0.30"));
        createCostume("酸黄瓜虾 #0001", "https://s.coze.cn/image/m69t4TcXMxk/", Costume.Rarity.RARE, new BigDecimal("0.30"));
        
        // 普通 COMMON
        createCostume("蒜鸟蒜鸟虾", "https://s.coze.cn/image/5ulebDlOOag/", Costume.Rarity.COMMON, new BigDecimal("0.60"));
        createCostume("质疑刀盾虾", "https://s.coze.cn/image/AdBdjeC0YmE/", Costume.Rarity.COMMON, new BigDecimal("0.60"));
        createCostume("ww虾", "https://s.coze.cn/image/65JTWrrY0fk/", Costume.Rarity.COMMON, new BigDecimal("0.60"));
        createCostume("别太荒谬虾", "https://s.coze.cn/image/_sxV-cn6ayA/", Costume.Rarity.COMMON, new BigDecimal("0.60"));
    }
    
    private void createCostume(String name, String image, Costume.Rarity rarity, BigDecimal probability) {
        Costume costume = new Costume();
        costume.setName(name);
        costume.setImage(image);
        costume.setRarity(rarity);
        costume.setProbability(probability);
        costumeRepository.save(costume);
    }
}
