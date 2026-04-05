package com.lobsterbox.dto;

public class CostumeDTO {
    private Long id;
    private String name;
    private String image;
    private String rarity;
    private String rarityText;
    private String style;
    private String uniqueId;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public String getRarity() { return rarity; }
    public void setRarity(String rarity) { this.rarity = rarity; }
    public String getRarityText() { return rarityText; }
    public void setRarityText(String rarityText) { this.rarityText = rarityText; }
    public String getStyle() { return style; }
    public void setStyle(String style) { this.style = style; }
    public String getUniqueId() { return uniqueId; }
    public void setUniqueId(String uniqueId) { this.uniqueId = uniqueId; }
}
