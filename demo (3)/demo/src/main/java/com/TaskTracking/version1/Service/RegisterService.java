package com.TaskTracking.version1.Service;

import com.TaskTracking.version1.Enum.UserRoleEnum;
import com.TaskTracking.version1.Models.UserModel;
import com.TaskTracking.version1.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    private final UserRepository userRepository;

    @Autowired
    public RegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Boolean register(String username, String password, String role) {
        if (userRepository.findByUsername(username).isPresent()) {
           return false;
        }
        else{
            UserModel user = new UserModel();
            user.setUsername(username);
            user.setPassword(password);
            if (role.equals("Admin")) {
                user.setRole(UserRoleEnum.ADMIN);
            } else {
                user.setRole(UserRoleEnum.USER);
            }
            userRepository.save(user);
        }
        return true;
    }
}
