package com.lobsterbox.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_costumes")
public class UserCostume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "costume_id")
    private Costume costume;
    
    @Column(unique = true)
    private String uniqueId;
    
    private LocalDateTime obtainedAt;
    
    @PrePersist
    protected void onCreate() {
        obtainedAt = LocalDateTime.now();
        if (uniqueId == null) {
            uniqueId = "LB" + System.currentTimeMillis();
        }
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Costume getCostume() { return costume; }
    public void setCostume(Costume costume) { this.costume = costume; }
    public String getUniqueId() { return uniqueId; }
    public void setUniqueId(String uniqueId) { this.uniqueId = uniqueId; }
    public LocalDateTime getObtainedAt() { return obtainedAt; }
    public void setObtainedAt(LocalDateTime obtainedAt) { this.obtainedAt = obtainedAt; }
}
