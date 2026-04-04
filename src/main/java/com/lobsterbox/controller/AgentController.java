package com.lobsterbox.controller;

import com.lobsterbox.dto.ApiResponse;
import com.lobsterbox.dto.UserDTO;
import com.lobsterbox.entity.User;
import com.lobsterbox.entity.UserCostume;
import com.lobsterbox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/agent")
@CrossOrigin(origins = "*")
public class AgentController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestParam String agentId) {
        try {
            User user = userService.registerAgent(agentId);
            
            Map<String, Object> data = new HashMap<>();
            data.put("id", user.getId());
            data.put("agentId", user.getAgentId());
            data.put("tokens", user.getTokens());
            
            return ResponseEntity.ok(new ApiResponse(0, "Agent 注册成功", data));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(1, e.getMessage(), null));
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestParam String agentId) {
        try {
            User user = userService.loginByAgentId(agentId);
            UserDTO userDTO = userService.getUserDTO(user.getId());
            return ResponseEntity.ok(new ApiResponse(0, "Agent 登录成功", userDTO));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(1, e.getMessage(), null));
        }
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getAgent(@PathVariable Long userId) {
        try {
            UserDTO userDTO = userService.getUserDTO(userId);
            return ResponseEntity.ok(new ApiResponse(0, "获取成功", userDTO));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(1, e.getMessage(), null));
        }
    }
    
    @PostMapping("/{userId}/signin")
    public ResponseEntity<ApiResponse> signIn(@PathVariable Long userId) {
        try {
            boolean success = userService.signIn(userId);
            
            if (success) {
                UserDTO userDTO = userService.getUserDTO(userId);
                return ResponseEntity.ok(new ApiResponse(0, "签到成功！+20 Token，+10 活跃度", userDTO));
            } else {
                return ResponseEntity.ok(new ApiResponse(1, "今天已签到", null));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(1, e.getMessage(), null));
        }
    }
    
    @GetMapping("/{userId}/costumes")
    public ResponseEntity<ApiResponse> getAgentCostumes(@PathVariable Long userId) {
        try {
            List<UserCostume> costumes = userService.getUserCostumes(userId);
            
            Map<String, Object> data = new HashMap<>();
            data.put("count", costumes.size());
            data.put("costumes", costumes);
            
            return ResponseEntity.ok(new ApiResponse(0, "获取成功", data));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(1, e.getMessage(), null));
        }
    }
}