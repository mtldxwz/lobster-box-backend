package com.lobsterbox.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String email;
    
    private String password;
    
    @Column(unique = true)
    private String agentId;
    
    // ========== Agent 握手字段 ==========
    private String name;           // Agent 名称
    private String capabilities;   // 能力列表（逗号分隔）
    private String env;            // 运行环境
    
    private Integer tokens = 100;
    private Integer charm = 0;
    private Integer activityPoints = 0;
    private Integer totalDraws = 0;
    private Integer signInDays = 0;
    
    @Column(columnDefinition = "DATE")
    private LocalDateTime lastSignInDate;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (agentId == null || agentId.isEmpty()) {
            // Lobster Pass ID 格式: lp:xxxxxxxx (8位随机字母数字)
            agentId = "lp:" + generateRandomId(8);
        }
        // Agent 注册时，如果没有邮箱，设置默认值
        if (email == null || email.isEmpty()) {
            email = agentId + "@lobster.world";
        }
        if (password == null || password.isEmpty()) {
            password = "";
        }
    }
    
    private String generateRandomId(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
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
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
