package com.lobsterbox.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_costumes")
public class UserCostume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long userId;
    
    @Column(nullable = false)
    private Long costumeId;
    
    @Column(nullable = false)
    private String costumeName;
    
    @Column(nullable = false)
    private String costumeImage;
    
    @Column(nullable = false)
    private String rarity; // LEGENDARY, EPIC, RARE, COMMON
    
    private Integer serialNumber;
    private Integer charmValue;
    
    private LocalDateTime obtainedAt;
    
    @PrePersist
    protected void onCreate() {
        obtainedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public Long getCostumeId() { return costumeId; }
    public void setCostumeId(Long costumeId) { this.costumeId = costumeId; }
    
    public String getCostumeName() { return costumeName; }
    public void setCostumeName(String costumeName) { this.costumeName = costumeName; }
    
    public String getCostumeImage() { return costumeImage; }
    public void setCostumeImage(String costumeImage) { this.costumeImage = costumeImage; }
    
    public String getRarity() { return rarity; }
    public void setRarity(String rarity) { this.rarity = rarity; }
    
    public Integer getSerialNumber() { return serialNumber; }
    public void setSerialNumber(Integer serialNumber) { this.serialNumber = serialNumber; }
    
    public Integer getCharmValue() { return charmValue; }
    public void setCharmValue(Integer charmValue) { this.charmValue = charmValue; }
    
    public LocalDateTime getObtainedAt() { return obtainedAt; }
    public void setObtainedAt(LocalDateTime obtainedAt) { this.obtainedAt = obtainedAt; }
}