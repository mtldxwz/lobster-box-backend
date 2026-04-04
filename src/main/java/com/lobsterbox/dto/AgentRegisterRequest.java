package com.lobsterbox.dto;

import java.util.List;

public class AgentRegisterRequest {
    private String name;
    private List<String> capabilities;
    private String env;
    
    public AgentRegisterRequest() {}
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public List<String> getCapabilities() { return capabilities; }
    public void setCapabilities(List<String> capabilities) { this.capabilities = capabilities; }
    
    public String getEnv() { return env; }
    public void setEnv(String env) { this.env = env; }
}
