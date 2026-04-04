package com.lobsterbox.service;

import com.lobsterbox.entity.User;
import com.lobsterbox.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Transactional
    public User loginOrCreate(String openid, String nickname, String avatar) {
        Optional<User> existUser = userRepository.findByOpenid(openid);
        if (existUser.isPresent()) {
            return existUser.get();
        }
        
        User user = new User();
        user.setOpenid(openid);
        user.setNickname(nickname != null ? nickname : "龙虾玩家");
        user.setAvatar(avatar);
        user.setToken(100);
        return userRepository.save(user);
    }
    
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
    
    @Transactional
    public User updateToken(Long userId, int tokenChange) {
        User user = getUserById(userId);
        if (user != null) {
            user.setToken(user.getToken() + tokenChange);
            return userRepository.save(user);
        }
        return null;
    }
    
    @Transactional
    public void incrementPityCount(Long userId) {
        User user = getUserById(userId);
        if (user != null) {
            user.setPityCount(user.getPityCount() + 1);
            userRepository.save(user);
        }
    }
    
    @Transactional
    public void resetPityCount(Long userId) {
        User user = getUserById(userId);
        if (user != null) {
            user.setPityCount(0);
            userRepository.save(user);
        }
    }
}
