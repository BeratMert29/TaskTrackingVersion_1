package com.TaskTracking.version1.Controller;

import com.TaskTracking.version1.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add/projectForUser/{projectName}/{username}")
    public String addProjectForUser(@PathVariable String projectName, @PathVariable String username) {
        return userService.addProjectToUser(projectName, username);
    }

    @PostMapping("/add/taskToUser/{projectName}/{taskName}/{username}")
    public String addTaskToUser(@PathVariable String projectName, @PathVariable String taskName, @PathVariable String username) {
        return userService.addTaskToUser(projectName, taskName, username);
    }

    @DeleteMapping("/delete/{projectName}/{username}")
    public String deleteProjectFromUser(@PathVariable String projectName, @PathVariable String username) {
        return userService.deleteProjectFromUser(projectName, username);
    }

    @DeleteMapping("/deleteTaskFromUser/{taskName}/{username}")
    public String deleteTaskFromUser(@PathVariable String taskName, @PathVariable String username) {
        return userService.deleteTaskFromUser(taskName, username);
    }

    @PostMapping("/add/sprintToUser/{sprintName}/{username}")
    public String addSprintToUser(@PathVariable String sprintName, @PathVariable String username) {
        return userService.addSprintToUser(sprintName, username);
    }

    @DeleteMapping("/deleteSprintFromUser/{sprintName}/{username}")
    public String deleteSprintFromUser(@PathVariable String sprintName, @PathVariable String username) {
        return userService.deleteSprintFromUser(sprintName, username);
    }

    @PostMapping("/addSubTask/{subTaskName}/{username}")
    public String addSubTaskToUser(@PathVariable String subTaskName, @PathVariable String username) {
        return userService.addSubTaskToUser(subTaskName, username);
    }

    @DeleteMapping("/deleteSubTaskFromUser/{subTaskName}/{username}")
    public String deleteSubTaskFromUser(@PathVariable String subTaskName, @PathVariable String username) {
        return userService.deleteSubTaskFromUser(subTaskName, username);
    }

}
