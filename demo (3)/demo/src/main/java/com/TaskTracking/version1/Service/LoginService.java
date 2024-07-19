package com.TaskTracking.version1.Service;

import com.TaskTracking.version1.Models.UserModel;
import com.TaskTracking.version1.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private final UserRepository userRepository;

    @Autowired
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Boolean login(String username, String password) {
        Optional<UserModel> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            UserModel user = userOptional.get();
            return user.getPassword().equals(password);
        }
        return false;
    }
}
