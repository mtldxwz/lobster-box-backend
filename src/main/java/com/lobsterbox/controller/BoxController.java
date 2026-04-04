package com.lobsterbox.controller;

import com.lobsterbox.dto.ApiResponse;
import com.lobsterbox.dto.DrawResult;
import com.lobsterbox.service.BoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/box")
public class BoxController {
    
    @Autowired
    private BoxService boxService;
    
    @PostMapping("/draw")
    public ApiResponse<DrawResult> draw(@RequestBody Map<String, Object> params) {
        Long userId = Long.valueOf(params.get("userId").toString());
        Integer count = params.get("count") != null ? (Integer) params.get("count") : 1;
        
        if (count != 1 && count != 10) {
            return ApiResponse.error(400, "count must be 1 or 10");
        }
        
        try {
            DrawResult result = boxService.draw(userId, count);
            return ApiResponse.success(result);
        } catch (RuntimeException e) {
            return ApiResponse.error(500, e.getMessage());
        }
    }
}
