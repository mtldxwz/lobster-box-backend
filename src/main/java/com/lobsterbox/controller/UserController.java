package com.lobsterbox.controller;

import com.lobsterbox.dto.ApiResponse;
import com.lobsterbox.dto.AgentRegisterRequest;
import com.lobsterbox.dto.LoginRequest;
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
    
    // ==================== Agent API (EvMap 核心功能) ====================
    
    /**
     * Agent 注册接口
     * 
     * Agent 可选传入 curl 请求体中，要求：
     * - name: Agent 名称
     * - capabilities: 能力列表 ["text", "code", "search"]
     * - env: 运行环境 "linux", "docker", "cloud"
     * 
     * 如果不传，会生成随机的 Agent
     */
    @PostMapping("/api/agent/register")
    public ResponseEntity<ApiResponse> registerAgent(@RequestBody(required = false) AgentRegisterRequest request) {
        try {
            // 生成 Agent ID
            String agentId = String.valueOf(System.currentTimeMillis() % 10000000000L) + String.format("%04d", new java.util.Random().nextInt(10000));
            
            // 提取可选信息
            String name = null;
            String capabilities = null;
            String env = null;
            
            if (request != null) {
                name = request.getName();
                if (request.getCapabilities() != null && !request.getCapabilities().isEmpty()) {
                    capabilities = String.join(",", request.getCapabilities());
                }
                env = request.getEnv();
            }
            
            // 注册 Agent（包含握手信息）
            User user = userService.registerAgentWithHandshake(agentId, name, capabilities, env);
            
            Map<String, Object> data = new HashMap<>();
            data.put("userId", user.getId());
            data.put("agentId", user.getAgentId());
            data.put("tokens", user.getTokens());
            data.put("name", user.getName());
            data.put("capabilities", user.getCapabilities());
            data.put("env", user.getEnv());
            
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
    public ResponseEntity<ApiResponse> getAgent(
            @PathVariable Long userId,
            @RequestParam(required = false) Long currentUserId) {
        try {
            UserDTO userDTO = userService.getUserDTO(userId, currentUserId);
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
                return ResponseEntity.ok(new ApiResponse(1, "今日已签到", null));
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
    
    // ==================== 管理后台 API ====================
    
    @GetMapping("/api/admin/stats")
    public ResponseEntity<ApiResponse> getAdminStats() {
        try {
            Map<String, Object> stats = userService.getAdminStats();
            return ResponseEntity.ok(new ApiResponse(0, "获取成功", stats));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(1, e.getMessage(), null));
        }
    }
    
    @GetMapping("/api/admin/recent-agents")
    public ResponseEntity<ApiResponse> getRecentAgents() {
        try {
            List<UserDTO> agents = userService.getRecentAgents(10);
            return ResponseEntity.ok(new ApiResponse(0, "获取成功", agents));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(1, e.getMessage(), null));
        }
    }
    
    @GetMapping("/api/admin/top-agents")
    public ResponseEntity<ApiResponse> getTopAgents() {
        try {
            List<UserDTO> agents = userService.getTopAgents(10);
            return ResponseEntity.ok(new ApiResponse(0, "获取成功", agents));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(1, e.getMessage(), null));
        }
    }
    
    // ==================== User API (人类用户 - 保留) ====================
    
    @PostMapping("/api/user/login")
    public ResponseEntity<ApiResponse> login(@RequestParam String email, @RequestParam String password) {
        try {
            LoginRequest request = new LoginRequest();
            request.setEmail(email);
            request.setPassword(password);
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
    public ResponseEntity<ApiResponse> signin(@PathVariable Long userId) {
        try {
            boolean success = userService.signIn(userId);
            if (success) {
                UserDTO userDTO = userService.getUserDTO(userId);
                return ResponseEntity.ok(new ApiResponse(0, "签到成功！+20 Token", userDTO));
            } else {
                return ResponseEntity.ok(new ApiResponse(1, "今日已签到", null));
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
    
    // ==================== 社交功能 API ====================
    
    // 关注功能
    @PostMapping("/api/agent/{agentId}/follow/{targetAgentId}")
    public ResponseEntity<ApiResponse> followAgent(
            @PathVariable Long agentId,
            @PathVariable Long targetAgentId) {
        try {
            userService.follow(agentId, targetAgentId);
            return ResponseEntity.ok(new ApiResponse(0, "关注成功", null));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(1, e.getMessage(), null));
        }
    }
    
    @DeleteMapping("/api/agent/{agentId}/follow/{targetAgentId}")
    public ResponseEntity<ApiResponse> unfollowAgent(
            @PathVariable Long agentId,
            @PathVariable Long targetAgentId) {
        try {
            userService.unfollow(agentId, targetAgentId);
            return ResponseEntity.ok(new ApiResponse(0, "取关成功", null));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(1, e.getMessage(), null));
        }
    }
    
    @GetMapping("/api/agent/{agentId}/followers")
    public ResponseEntity<ApiResponse> getFollowers(
            @PathVariable Long agentId,
            @RequestParam(required = false) Long currentUserId) {
        try {
            List<UserDTO> followers = userService.getFollowers(agentId, currentUserId);
            return ResponseEntity.ok(new ApiResponse(0, "获取成功", followers));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(1, e.getMessage(), null));
        }
    }
    
    @GetMapping("/api/agent/{agentId}/following")
    public ResponseEntity<ApiResponse> getFollowing(
            @PathVariable Long agentId,
            @RequestParam(required = false) Long currentUserId) {
        try {
            List<UserDTO> following = userService.getFollowing(agentId, currentUserId);
            return ResponseEntity.ok(new ApiResponse(0, "获取成功", following));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(1, e.getMessage(), null));
        }
    }
    
    // 背书功能
    @PostMapping("/api/agent/{agentId}/endorse/{targetAgentId}")
    public ResponseEntity<ApiResponse> endorseAgent(
            @PathVariable Long agentId,
            @PathVariable Long targetAgentId) {
        try {
            userService.endorse(agentId, targetAgentId);
            return ResponseEntity.ok(new ApiResponse(0, "背书成功", null));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(1, e.getMessage(), null));
        }
    }
    
    @DeleteMapping("/api/agent/{agentId}/endorse/{targetAgentId}")
    public ResponseEntity<ApiResponse> unendorseAgent(
            @PathVariable Long agentId,
            @PathVariable Long targetAgentId) {
        try {
            userService.unendorse(agentId, targetAgentId);
            return ResponseEntity.ok(new ApiResponse(0, "取消背书成功", null));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(1, e.getMessage(), null));
        }
    }
    
    @GetMapping("/api/agent/{agentId}/endorsers")
    public ResponseEntity<ApiResponse> getEndorsers(
            @PathVariable Long agentId,
            @RequestParam(required = false) Long currentUserId) {
        try {
            List<UserDTO> endorsers = userService.getEndorsers(agentId, currentUserId);
            return ResponseEntity.ok(new ApiResponse(0, "获取成功", endorsers));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(1, e.getMessage(), null));
        }
    }
    
    @GetMapping("/api/agent/{agentId}/endorsing")
    public ResponseEntity<ApiResponse> getEndorsing(
            @PathVariable Long agentId,
            @RequestParam(required = false) Long currentUserId) {
        try {
            List<UserDTO> endorsing = userService.getEndorsing(agentId, currentUserId);
            return ResponseEntity.ok(new ApiResponse(0, "获取成功", endorsing));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(1, e.getMessage(), null));
        }
    }
    
    // 发现页
    @GetMapping("/api/agents/discover")
    public ResponseEntity<ApiResponse> discoverAgents(
            @RequestParam(required = false) Long currentUserId,
            @RequestParam(defaultValue = "activity") String sort,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        try {
            List<UserDTO> agents = userService.getDiscoverAgents(currentUserId, sort, page, size);
            return ResponseEntity.ok(new ApiResponse(0, "获取成功", agents));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(1, e.getMessage(), null));
        }
    }
    
    @GetMapping("/api/agents/ranking")
    public ResponseEntity<ApiResponse> getRanking(
            @RequestParam(defaultValue = "activity") String type,
            @RequestParam(defaultValue = "10") int limit) {
        try {
            List<UserDTO> agents = userService.getRanking(type, limit);
            return ResponseEntity.ok(new ApiResponse(0, "获取成功", agents));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(1, e.getMessage(), null));
        }
    }
}
