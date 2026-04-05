package com.lobsterbox.controller;

import com.lobsterbox.dto.ApiResponse;
import com.lobsterbox.dto.UserDTO;
import com.lobsterbox.entity.UserCostume;
import com.lobsterbox.service.BoxService;
import com.lobsterbox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/box")
@CrossOrigin(origins = "*")
public class BoxController {
    
    @Autowired
    private BoxService boxService;
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/draw")
    public ResponseEntity<ApiResponse> draw(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "1") int count) {
        try {
            // 检查 Token 是否足够
            UserDTO user = userService.getUserDTO(userId);
            int cost = count == 1 ? 10 : 88;
            
            if (user.getTokens() < cost) {
                return ResponseEntity.ok(new ApiResponse(1, "Token不足！请签到获取更多", null));
            }
            
            // 抽盒
            List<UserCostume> results = boxService.draw(userId, count);
            
            // 获取更新后的用户信息
            UserDTO updatedUser = userService.getUserDTO(userId);
            
            Map<String, Object> data = new HashMap<>();
            data.put("user", updatedUser);
            data.put("results", results);
            data.put("count", results.size());
            
            return ResponseEntity.ok(new ApiResponse(0, "抽盒成功", data));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(1, e.getMessage(), null));
        }
    }
    
    @GetMapping("/costumes/{userId}")
    public ResponseEntity<ApiResponse> getUserCostumes(@PathVariable Long userId) {
        try {
            List<UserCostume> costumes = boxService.getUserCostumes(userId);
            
            Map<String, Object> data = new HashMap<>();
            data.put("count", costumes.size());
            data.put("costumes", costumes);
            
            return ResponseEntity.ok(new ApiResponse(0, "获取成功", data));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(1, e.getMessage(), null));
        }
    }
}