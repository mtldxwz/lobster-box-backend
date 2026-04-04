package com.lobsterbox.controller;

import com.lobsterbox.dto.ApiResponse;
import com.lobsterbox.dto.LoginRequest;
import com.lobsterbox.dto.RegisterRequest;
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
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    // ========== Agent API (无邮箱密码) ==========
    
    @PostMapping("/api/agent/register")
    public ResponseEntity<ApiResponse> registerAgent(@RequestParam(required = false) String agentId) {
        try {
            if (agentId == null || agentId.isEmpty()) {
                agentId = "agent_" + UUID.randomUUID().toString().substring(0, 8);
            }
            User user = userService.registerAgent(agentId);
            Map<String, Object> data = new HashMap<>();
            data.put("userId", user.getId());
            data.put("agentId", user.getAgentId());
            data.put("tokens", user.getTokens());
            return ResponseEntity.ok(new ApiResponse(0, "Agent 注册成功", data));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(1, e.getMessage(), null));
        }
    }
    
    @PostMapping("/api/agent/login")
    public ResponseEntity<ApiResponse> loginAgent(@RequestParam String agentId) {
        try {
            User user = userService.loginByAgentId(agentId);
            UserDTO userDTO = userService.getUserDTO(user.getId());
            return ResponseEntity.ok(new ApiResponse(0, "Agent 登录成功", userDTO));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(1, e.getMessage(), null));
        }
    }
    
    @GetMapping("/api/agent/{userId}")
    public ResponseEntity<ApiResponse> getAgent(@PathVariable Long userId) {
        try {
            UserDTO userDTO = userService.getUserDTO(userId);
            return ResponseEntity.ok(new ApiResponse(0, "获取成功", userDTO));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(1, e.getMessage(), null));
        }
    }
    
    @PostMapping("/api/agent/{userId}/signin")
    public ResponseEntity<ApiResponse> signinAgent(@PathVariable Long userId) {
        try {
            boolean success = userService.signIn(userId);
            if (success) {
                UserDTO userDTO = userService.getUserDTO(userId);
                return ResponseEntity.ok(new ApiResponse(0, "签到成功", userDTO));
            } else {
                return ResponseEntity.ok(new ApiResponse(1, "今天已签到", null));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(1, e.getMessage(), null));
        }
    }
    
    @GetMapping("/api/agent/{userId}/costumes")
    public ResponseEntity<ApiResponse> getAgentCostumes(@PathVariable Long userId) {
        try {
            List<UserCostume> costumes = userService.getUserCostumes(userId);
            return ResponseEntity.ok(new ApiResponse(0, "获取成功", costumes));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(1, e.getMessage(), null));
        }
    }
    
    // ========== User API (邮箱密码) ==========
    
    @PostMapping("/api/user/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest request) {
        try {
            User user = userService.register(request);
            
            Map<String, Object> data = new HashMap<>();
            data.put("userId", user.getId());
            data.put("email", user.getEmail());
            data.put("agentId", user.getAgentId());
            data.put("tokens", user.getTokens());
            
            return ResponseEntity.ok(new ApiResponse(0, "注册成功", data));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(1, e.getMessage(), null));
        }
    }
    
    @PostMapping("/api/user/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        try {
            User user = userService.login(request);
            UserDTO userDTO = userService.getUserDTO(user.getId());
            return ResponseEntity.ok(new ApiResponse(0, "登录成功", userDTO));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(1, e.getMessage(), null));
        }
    }
    
    @GetMapping("/api/user/{userId}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable Long userId) {
        try {
            UserDTO userDTO = userService.getUserDTO(userId);
            return ResponseEntity.ok(new ApiResponse(0, "获取成功", userDTO));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(1, e.getMessage(), null));
        }
    }
    
    @PostMapping("/api/user/{userId}/signin")
    public ResponseEntity<ApiResponse> signIn(@PathVariable Long userId) {
        try {
            boolean success = userService.signIn(userId);
            
            if (success) {
                UserDTO userDTO = userService.getUserDTO(userId);
                return ResponseEntity.ok(new ApiResponse(0, "签到成功！+20 Token", userDTO));
            } else {
                return ResponseEntity.ok(new ApiResponse(1, "今天已签到", null));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(1, e.getMessage(), null));
        }
    }
    
    @GetMapping("/api/user/{userId}/costumes")
    public ResponseEntity<ApiResponse> getUserCostumes(@PathVariable Long userId) {
        try {
            List<UserCostume> costumes = userService.getUserCostumes(userId);
            return ResponseEntity.ok(new ApiResponse(0, "获取成功", costumes));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(1, e.getMessage(), null));
        }
    }
}
