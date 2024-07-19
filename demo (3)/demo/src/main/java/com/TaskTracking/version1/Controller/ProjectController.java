package com.TaskTracking.version1.Controller;

import com.TaskTracking.version1.Models.dto.ProjectDTO;
import com.TaskTracking.version1.Models.dto.SprintDTO;
import com.TaskTracking.version1.Service.ProjectService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/create/project/{username}")
    public String createProject(@RequestBody ProjectDTO projectDTO, @PathVariable String username) {
        return projectService.addProjectToSystem(projectDTO.getProjectName(), projectDTO.getLastDate(), projectDTO.getProjectDescription(), username);
    }

    @DeleteMapping("/delete/project/{username}/{projectName}")
    public String deleteProject(@PathVariable String username, @PathVariable String projectName) {
        return projectService.deleteProjectFromSystem(projectName, username);
    }

    @PostMapping("/update/project/{projectName}")
    public String updateProject(@RequestBody ProjectDTO newProjectDTO, @PathVariable String projectName) {
        return projectService.updateProject(projectName, newProjectDTO);
    }

    @PostMapping("/create/sprint/{username}/{projectName}")
    public String createSprint(@RequestBody SprintDTO sprintDTO, @PathVariable String username, @PathVariable String projectName) {
        return projectService.createSprintForProject(projectName, sprintDTO, username);
    }

    @DeleteMapping("/delete/sprint/{username}/{projectName}/{sprintName}")
    public String deleteSprint(@PathVariable String username, @PathVariable String projectName, @PathVariable String sprintName) {
        return projectService.deleteSprintForProject(projectName,sprintName,username);
    }

    @PostMapping("/update/sprint/{projectName}")
    public String updateSprint(@RequestBody SprintDTO sprintDTO, @PathVariable String projectName) {
        return projectService.updateSprintForProject(projectName, sprintDTO);
    }

}


