package com.lobsterbox.dto;

import java.time.LocalDateTime;

public class UserDTO {
    private Long id;
    private String email;
    private String agentId;
    private String name;
    private String capabilities;
    private String env;
    private Integer tokens;
    private Integer charm;
    private Integer activityPoints;
    private Integer totalDraws;
    private Integer signInDays;
    private LocalDateTime lastSignInDate;
    private LocalDateTime createdAt;
    private String certificationLevel;
    private int legendaryCount;
    private int epicCount;
    private int rareCount;
    private int commonCount;
    private int costumeCount;
    
    // 社交统计
    private long followerCount;
    private long followingCount;
    private long endorsementCount;
    private long endorsedCount;
    
    // 当前用户与该用户的关系（用于前端判断按钮状态）
    private Boolean isFollowing;
    private Boolean isEndorsing;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getAgentId() { return agentId; }
    public void setAgentId(String agentId) { this.agentId = agentId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getCapabilities() { return capabilities; }
    public void setCapabilities(String capabilities) { this.capabilities = capabilities; }
    
    public String getEnv() { return env; }
    public void setEnv(String env) { this.env = env; }
    
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
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
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
    
    public int getCostumeCount() { return costumeCount; }
    public void setCostumeCount(int costumeCount) { this.costumeCount = costumeCount; }
    
    public long getFollowerCount() { return followerCount; }
    public void setFollowerCount(long followerCount) { this.followerCount = followerCount; }
    
    public long getFollowingCount() { return followingCount; }
    public void setFollowingCount(long followingCount) { this.followingCount = followingCount; }
    
    public long getEndorsementCount() { return endorsementCount; }
    public void setEndorsementCount(long endorsementCount) { this.endorsementCount = endorsementCount; }
    
    public long getEndorsedCount() { return endorsedCount; }
    public void setEndorsedCount(long endorsedCount) { this.endorsedCount = endorsedCount; }
    
    public Boolean getIsFollowing() { return isFollowing; }
    public void setIsFollowing(Boolean isFollowing) { this.isFollowing = isFollowing; }
    
    public Boolean getIsEndorsing() { return isEndorsing; }
    public void setIsEndorsing(Boolean isEndorsing) { this.isEndorsing = isEndorsing; }
}
