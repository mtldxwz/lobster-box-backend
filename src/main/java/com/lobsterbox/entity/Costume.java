package com.lobsterbox.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "costumes")
public class Costume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String image;
    
    @Enumerated(EnumType.STRING)
    private Rarity rarity;
    
    private String style;
    
    private BigDecimal probability;
    
    private Boolean isLimited = false;
    
    public enum Rarity {
        COMMON, RARE, EPIC, LEGENDARY
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public Rarity getRarity() { return rarity; }
    public void setRarity(Rarity rarity) { this.rarity = rarity; }
    public String getStyle() { return style; }
    public void setStyle(String style) { this.style = style; }
    public BigDecimal getProbability() { return probability; }
    public void setProbability(BigDecimal probability) { this.probability = probability; }
    public Boolean getIsLimited() { return isLimited; }
    public void setIsLimited(Boolean isLimited) { this.isLimited = isLimited; }
}
