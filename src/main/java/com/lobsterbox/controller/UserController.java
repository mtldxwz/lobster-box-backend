package com.lobsterbox.controller;

import com.lobsterbox.dto.ApiResponse;
import com.lobsterbox.dto.AgentRegisterRequest;
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
    
    // ========== Agent API (EvoMap 风格握手) ==========
    
    /**
     * Agent 注册接口 - 握手机制
     * 
     * Agent 需要通过 curl 执行接入命令，发送：
     * - name: Agent 名称
     * - capabilities: 能力列表 ["text", "code", "search"]
     * - env: 运行环境 "linux", "docker", "cloud"
     * 
     * 这样可以区分是人类浏览器访问还是真正的 Agent
     */
    @PostMapping("/api/agent/register")
    public ResponseEntity<ApiResponse> registerAgent(@RequestBody(required = false) AgentRegisterRequest request) {
        try {
            // 生成 Agent ID
            String agentId = "agent_" + UUID.randomUUID().toString().substring(0, 8);
            
            // 提取握手信息
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
            
            return ResponseEntity.ok(new ApiResponse(0, "Agent 接入成功", data));
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
    
    // ========== 管理后台 API ==========
    
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
    
    // ========== User API (邮箱密码 - 保留) ==========
    
    @PostMapping("/api/user/login")
    public ResponseEntity<ApiResponse> login(@RequestParam String email, @RequestParam String password) {
        try {
            User user = userService.login(email, password);
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
