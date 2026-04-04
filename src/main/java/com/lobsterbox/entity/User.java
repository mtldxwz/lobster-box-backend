package com.lobsterbox.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(unique = true)
    private String agentId;
    
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
            agentId = "AGENT-" + String.format("%06d", id != null ? id : (int)(Math.random() * 1000000));
        }
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