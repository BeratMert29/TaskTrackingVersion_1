package com.TaskTracking.version1.Controller;

import com.TaskTracking.version1.Models.dto.SubTaskDTO;
import com.TaskTracking.version1.Models.dto.TaskDTO;
import com.TaskTracking.version1.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create/task/{projectName}/{username}")
    public String createTaskForProject(@RequestBody TaskDTO request, @PathVariable String projectName, @PathVariable String username) {
        return taskService.createTaskForProject(request, projectName, username);
    }

    @DeleteMapping("/delete/task/{username}/{projectName}/{taskName}")
    public String deleteTaskForProject(@PathVariable String taskName, @PathVariable String username, @PathVariable String projectName) {
        return taskService.deleteTaskFromProject(projectName, taskName, username);
    }

    @PostMapping("/update/task/{projectName}/{taskName}")
    public String updateTask(@RequestBody TaskDTO request, @PathVariable String projectName, @PathVariable String taskName) {
        return taskService.updateTask(projectName, taskName, request);
    }

    @PostMapping("/add/taskToSprint/{sprintName}/{taskName}")
    public String addTaskToSprint(@PathVariable String sprintName, @PathVariable String taskName) {
        return taskService.addTaskToSprint(sprintName, taskName);
    }

    @PostMapping("/addSubTask/{parentTask}")
    public String addSubTask(@RequestBody SubTaskDTO subTaskDTO, @PathVariable String parentTask) {
        return taskService.addSubTask(subTaskDTO, parentTask);
    }

    @DeleteMapping("/deleteSubTask/{subTaskName}")
    public String deleteSubTask(@PathVariable String subTaskName) {
        return taskService.deleteSubTask(subTaskName);
    }

    @PostMapping("/updateSubTask/{subTaskName}")
    public String updateSubTask(@RequestBody SubTaskDTO updatedSubTaskDTO, @PathVariable String subTaskName) {
        return taskService.updateSubTask(updatedSubTaskDTO, subTaskName);
    }
}
