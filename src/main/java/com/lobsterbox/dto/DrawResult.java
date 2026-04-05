package com.lobsterbox.dto;

import java.util.List;

public class DrawResult {
    private List<CostumeDTO> costumes;
    private Integer newToken;
    private Integer pityCount;
    
    public List<CostumeDTO> getCostumes() { return costumes; }
    public void setCostumes(List<CostumeDTO> costumes) { this.costumes = costumes; }
    public Integer getNewToken() { return newToken; }
    public void setNewToken(Integer newToken) { this.newToken = newToken; }
    public Integer getPityCount() { return pityCount; }
    public void setPityCount(Integer pityCount) { this.pityCount = pityCount; }
}
