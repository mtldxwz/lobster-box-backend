package com.lobsterbox.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sign_in_logs")
public class SignInLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long userId;
    
    @Column(columnDefinition = "DATE")
    private LocalDateTime signInDate;
    
    private Integer tokensEarned;
    
    @PrePersist
    protected void onCreate() {
        if (signInDate == null) {
            signInDate = LocalDateTime.now();
        }
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public LocalDateTime getSignInDate() { return signInDate; }
    public void setSignInDate(LocalDateTime signInDate) { this.signInDate = signInDate; }
    
    public Integer getTokensEarned() { return tokensEarned; }
    public void setTokensEarned(Integer tokensEarned) { this.tokensEarned = tokensEarned; }
}