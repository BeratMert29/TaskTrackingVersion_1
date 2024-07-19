package com.TaskTracking.version1.Service;

import com.TaskTracking.version1.Enum.StatusEnum;
import com.TaskTracking.version1.Enum.UserRoleEnum;
import com.TaskTracking.version1.Models.ProjectModel;
import com.TaskTracking.version1.Models.SprintModel;
import com.TaskTracking.version1.Models.TaskModel;
import com.TaskTracking.version1.Models.UserModel;
import com.TaskTracking.version1.Models.dto.ProjectDTO;
import com.TaskTracking.version1.Models.dto.SprintDTO;
import com.TaskTracking.version1.Repository.ProjectRepository;
import com.TaskTracking.version1.Repository.SprintRepository;
import com.TaskTracking.version1.Repository.TaskRepository;
import com.TaskTracking.version1.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Service
public class ProjectService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final SprintRepository sprintRepository;

    @Autowired
    public ProjectService(UserRepository userRepository, ProjectRepository projectRepository, TaskRepository taskRepository, SprintRepository sprintRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.sprintRepository = sprintRepository;
    }

    public String addProjectToSystem(String projectName, Date lastDate, String description, String username) {
        Optional<UserModel> userModel = userRepository.findByUsername(username);
        if (userModel.isEmpty() || !userModel.get().getRole().equals(UserRoleEnum.ADMIN)) {
            return "You need to be admin to add a task to this project";
        }
        ProjectModel projectModel = new ProjectModel();
        projectModel.setProjectName(projectName);
        projectModel.setStatus(StatusEnum.ACTIVE);
        projectModel.setLastDate(lastDate);
        projectModel.setProjectDescription(description);
        projectRepository.save(projectModel);
        return "Project added to system";
    }

    public String deleteProjectFromSystem(String projectName, String username) {
        Optional<UserModel> userModelOptional = userRepository.findByUsername(username);
        if (userModelOptional.isEmpty() || !userModelOptional.get().getRole().equals(UserRoleEnum.ADMIN)) {
            return "You need to be admin to delete this project";
        }
        Optional<ProjectModel> projectModelOptional = projectRepository.findByProjectName(projectName);
        if (projectModelOptional.isEmpty()) {
            return "Project is not in the system";
        }
        ProjectModel projectModel = projectModelOptional.get();
        for (TaskModel taskModel : projectModel.getTaskModels()) {
            taskModel.setStatus(StatusEnum.INACTIVE);
            taskRepository.save(taskModel);
        }
        projectModel.setStatus(StatusEnum.INACTIVE);
        projectRepository.save(projectModel);
        return "Project status is now inactive";
    }

    public String updateProject(String projectName, ProjectDTO updatedProjectModel) {
        Optional<ProjectModel> projectModelOptional = projectRepository.findByProjectName(projectName);
        if (projectModelOptional.isPresent()) {
            ProjectModel projectModel = projectModelOptional.get();
            if (updatedProjectModel.getProjectName() != null) projectModel.setProjectName(updatedProjectModel.getProjectName());
            if (updatedProjectModel.getLastDate() != null)  projectModel.setLastDate(updatedProjectModel.getLastDate());
            if (updatedProjectModel.getProjectDescription() != null) projectModel.setProjectDescription(updatedProjectModel.getProjectDescription());
            if (updatedProjectModel.getStatus() != null) projectModel.setStatus(updatedProjectModel.getStatus());
            projectRepository.save(projectModel);
            return "Project updated";
        }
        return "Project is not in system";
    }

    public String createSprintForProject(String projectName, SprintDTO sprintDTO, String username) {
        Optional<UserModel> userModelOptional = userRepository.findByUsername(username);
        if (userModelOptional.isEmpty() || !userModelOptional.get().getRole().equals(UserRoleEnum.ADMIN)) {
            return "You need to be admin to add a sprint to this project";
        }
        Optional<ProjectModel> projectModelOptional = projectRepository.findByProjectName(projectName);
        if (projectModelOptional.isPresent() && sprintDTO.getDate().before(projectModelOptional.get().getLastDate())) {
            ProjectModel projectModel = projectModelOptional.get();
            SprintModel sprintModel = new SprintModel();
            sprintModel.setSprintDate(sprintDTO.getDate());
            sprintModel.setProjectModel(projectModel);
            sprintModel.setSprintDescription(sprintDTO.getDescription());
            sprintModel.setSprintStatus(StatusEnum.ACTIVE);
            sprintModel.setSprintName(sprintDTO.getSprintName());
            projectModel.getSprints().add(sprintModel);
            projectRepository.save(projectModel);
            sprintRepository.save(sprintModel);
            return "Sprint created successfully.";
        } else {
            return "Project not found or due date is passed";
        }
    }

    public String deleteSprintForProject(String projectName, String sprintName, String username) {
        Optional<UserModel> userModelOptional = userRepository.findByUsername(username);
        if (userModelOptional.isEmpty() || !userModelOptional.get().getRole().equals(UserRoleEnum.ADMIN)) {
            return "You need to be admin to delete a sprint from this project";
        }
        Optional<ProjectModel> projectModelOptional = projectRepository.findByProjectName(projectName);
        if (projectModelOptional.isEmpty()) {
            return "Project not found.";
        }
        ProjectModel projectModel = projectModelOptional.get();
        Optional<SprintModel> sprintModelOptional = projectModel.getSprints().stream()
                .filter(sprint -> sprint.getSprintName().equals(sprintName))
                .findFirst();
        if (sprintModelOptional.isEmpty()) {
            return "Sprint not found in the specified project.";
        }
        SprintModel sprintModel = sprintModelOptional.get();
        sprintModel.setSprintStatus(StatusEnum.INACTIVE);
        sprintRepository.save(sprintModel);
        projectRepository.save(projectModel);
        return "Sprint status set to inactive successfully.";
    }

    public String updateSprintForProject(String projectName, SprintDTO sprintDTO) {
        Optional<ProjectModel> projectModelOptional = projectRepository.findByProjectName(projectName);
        if (projectModelOptional.isEmpty()) {
            return "Project not found.";
        }
        ProjectModel projectModel = projectModelOptional.get();
        Optional<SprintModel> sprintModelOptional = sprintRepository.findByProjectModel(projectModel);
        if (sprintModelOptional.isEmpty()) {
            return "Sprint not found in the specified project.";
        }
        SprintModel sprintModel = sprintModelOptional.get();
        sprintModel.setSprintDescription(sprintDTO.getDescription());
        sprintModel.setSprintName(sprintDTO.getSprintName());
        sprintModel.setSprintDate(sprintDTO.getDate());
        sprintRepository.save(sprintModel);
        projectRepository.save(projectModel);
        return "Sprint is updated successfully.";
    }

}
