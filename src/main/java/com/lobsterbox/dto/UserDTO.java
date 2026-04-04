package com.lobsterbox.dto;

import java.time.LocalDateTime;

public class UserDTO {
    private Long id;
    private String email;
    private String agentId;
    private Integer tokens;
    private Integer charm;
    private Integer activityPoints;
    private Integer totalDraws;
    private Integer signInDays;
    private LocalDateTime lastSignInDate;
    private String certificationLevel; // 根据装扮计算
    private int legendaryCount;
    private int epicCount;
    private int rareCount;
    private int commonCount;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getAgentId() { return agentId; }
    public void setAgentId(String agentId) { this.agentId = agentId; }
    
    public Integer getTokens() { return tokens; }
    public void setTokens(Integer tokens) { this.tokens = tokens; }
    
    public Integer getCharm() { return charm; }
    public void setCharm(Integer charm) { this.charm = charm; }
    
    public Integer getActivityPoints() { return activityPoints; }
    public void setActivityPoints(Integer activityPoints) { this.activityPoints = activityPoints; }
    
    public Integer getTotalDraws() { return totalDraws; }
    public void setTotalDraws(Integer totalDraws) { this.totalDraws = totalDraws; }
    
    public Integer getSignInDays() { return signInDays; }
    public void setSignInDays(Integer signInDays) { this.signInDays = signInDays; }
    
    public LocalDateTime getLastSignInDate() { return lastSignInDate; }
    public void setLastSignInDate(LocalDateTime lastSignInDate) { this.lastSignInDate = lastSignInDate; }
    
    public String getCertificationLevel() { return certificationLevel; }
    public void setCertificationLevel(String certificationLevel) { this.certificationLevel = certificationLevel; }
    
    public int getLegendaryCount() { return legendaryCount; }
    public void setLegendaryCount(int legendaryCount) { this.legendaryCount = legendaryCount; }
    
    public int getEpicCount() { return epicCount; }
    public void setEpicCount(int epicCount) { this.epicCount = epicCount; }
    
    public int getRareCount() { return rareCount; }
    public void setRareCount(int rareCount) { this.rareCount = rareCount; }
    
    public int getCommonCount() { return commonCount; }
    public void setCommonCount(int commonCount) { this.commonCount = commonCount; }
}