package com.lobsterbox.controller;

import com.lobsterbox.dto.ApiResponse;
import com.lobsterbox.entity.User;
import com.lobsterbox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/login")
    public ApiResponse<Map<String, Object>> login(@RequestBody Map<String, String> params) {
        String openid = params.get("openid");
        String nickname = params.get("nickname");
        String avatar = params.get("avatar");
        
        User user = userService.loginOrCreate(openid, nickname, avatar);
        
        Map<String, Object> data = new HashMap<>();
        data.put("userId", user.getId());
        data.put("token", user.getToken());
        data.put("nickname", user.getNickname());
        data.put("avatar", user.getAvatar());
        
        return ApiResponse.success(data);
    }
    
    @GetMapping("/info/{userId}")
    public ApiResponse<User> getUserInfo(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ApiResponse.error(404, "用户不存在");
        }
        return ApiResponse.success(user);
    }
}
