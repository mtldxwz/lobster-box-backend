package com.lobsterbox.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "endorsements", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"endorser_id", "endorsed_id"})
})
public class Endorsement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "endorser_id", nullable = false)
    private Long endorserId;
    
    @Column(name = "endorsed_id", nullable = false)
    private Long endorsedId;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getEndorserId() { return endorserId; }
    public void setEndorserId(Long endorserId) { this.endorserId = endorserId; }
    
    public Long getEndorsedId() { return endorsedId; }
    public void setEndorsedId(Long endorsedId) { this.endorsedId = endorsedId; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
