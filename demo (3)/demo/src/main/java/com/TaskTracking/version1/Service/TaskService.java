package com.TaskTracking.version1.Service;

import com.TaskTracking.version1.Enum.StatusEnum;
import com.TaskTracking.version1.Enum.UserRoleEnum;
import com.TaskTracking.version1.Models.*;
import com.TaskTracking.version1.Models.dto.SubTaskDTO;
import com.TaskTracking.version1.Models.dto.TaskDTO;
import com.TaskTracking.version1.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final SprintRepository sprintRepository;
    private final SubTaskRepository subTaskRepository;

    @Autowired
    public TaskService(UserRepository userRepository, ProjectRepository projectRepository, TaskRepository taskRepository, SprintRepository sprintRepository, SubTaskRepository subTaskRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.sprintRepository = sprintRepository;
        this.subTaskRepository = subTaskRepository;
    }

    public String createTaskForProject(TaskDTO taskDTO, String projectName, String username) {
        Optional<UserModel> userModel = userRepository.findByUsername(username);
        if (userModel.isEmpty() || !userModel.get().getRole().equals(UserRoleEnum.ADMIN)) {
            return "You need to be admin to add a task to this project";
        }
        Optional<ProjectModel> projectModelOptional = projectRepository.findByProjectName(projectName);
        if (projectModelOptional.isEmpty()) {
            return "Project not found";
        }
        ProjectModel existingProject = projectModelOptional.get();
        if (existingProject.getLastDate().before(new Date()) || !existingProject.getStatus().equals(StatusEnum.ACTIVE)) {
            return "Cannot add task to project. Due date is passed or project is not active";
        }
        List<TaskModel> tasks = taskRepository.findAllByProjectModel(existingProject);
        if (!tasks.isEmpty()) {
            return "Only one task can be added to this project";
        }
        TaskModel taskModel = new TaskModel();
        taskModel.setStatus(StatusEnum.ACTIVE);
        taskModel.setTaskName(taskDTO.getTaskName());
        taskModel.setTaskType(taskDTO.getTaskType());
        taskModel.setDescription(taskDTO.getDescription());
        taskModel.setLastDate(taskDTO.getLastDate());
        taskModel.setProjectModel(existingProject);
        taskRepository.save(taskModel);
        existingProject.getTaskModels().add(taskModel);
        projectRepository.save(existingProject);
        return "Task added to project";
    }

    public String deleteTaskFromProject(String projectName, String taskName, String username) {
        Optional<ProjectModel> projectModelOptional = projectRepository.findByProjectName(projectName);
        if (projectModelOptional.isEmpty()) {
            return "Project not found";
        }
        Optional<UserModel> userModel = userRepository.findByUsername(username);
        if (userModel.isEmpty() || !userModel.get().getRole().equals(UserRoleEnum.ADMIN)) {
            return "You need to be admin to delete a task from this project";
        }
        ProjectModel existingProject = projectModelOptional.get();
        Optional<TaskModel> taskModelOptional  = existingProject.getTaskModels().stream()
                .filter(taskModel -> taskModel.getTaskName().equals(taskName))
                .findFirst();
        if (taskModelOptional.isEmpty()) {
            return "Task not found";
        }else{
            TaskModel task = taskModelOptional.get();
            task.setStatus(StatusEnum.INACTIVE);
            taskRepository.save(task);
            projectRepository.save(existingProject);
            return "Task deleted from project";
        }
    }

    public String updateTask(String projectName, String taskName, TaskDTO taskDTO) {
        Optional<ProjectModel> projectModelOptional = projectRepository.findByProjectName(projectName);
        if (projectModelOptional.isEmpty()) {
            return "Project not found";
        }
        ProjectModel existingProject = projectModelOptional.get();
        Optional<TaskModel> taskModels = existingProject.getTaskModels().stream()
                .filter(taskModel -> taskModel.getTaskName().equals(taskName))
                .findFirst();
        if (taskModels.isPresent()) {
            TaskModel taskModel = taskModels.get();
            taskModel.setTaskName(taskDTO.getTaskName());
            taskModel.setTaskType(taskDTO.getTaskType());
            taskModel.setLastDate(taskDTO.getLastDate());
            taskModel.setDescription(taskDTO.getDescription());
            taskRepository.save(taskModel);
            projectRepository.save(existingProject);
            return "Task updated in project " + existingProject.getProjectName();
        } else {
            return "Task not found";
        }
    }

    public String addTaskToSprint(String sprintName, String taskName){
        Optional<SprintModel> sprintModel = sprintRepository.findBySprintName(sprintName);

        if (sprintModel.isEmpty()) return "Sprint not found";
        Optional<TaskModel> taskModelOptional = taskRepository.findByTaskName(taskName);
        if (taskModelOptional.isEmpty()) return "Task not found";

        SprintModel sprintModel1 = sprintModel.get();
        TaskModel taskModel = taskModelOptional.get();

        if (taskModel.getSprintModel() != null) return "Task already exists in sprint " + taskModel.getSprintModel();

        sprintModel1.getTasks().add(taskModel);
        sprintRepository.save(sprintModel1);
        taskModel.setSprintModel(sprintModel1);
        taskRepository.save(taskModel);
        return "Task added to sprint " + sprintName;
    }

    public String addSubTask(SubTaskDTO subTaskDTO, String parentTask){
        Optional<TaskModel> taskModelOptional = taskRepository.findByTaskName(parentTask);
        if (taskModelOptional.isEmpty()) return "Task not found";
        TaskModel parentTaskModel = taskModelOptional.get();
        SubTaskModel subTaskModel = new SubTaskModel();
        subTaskModel.setTaskName(subTaskDTO.getTaskName());
        subTaskModel.setTaskType(subTaskDTO.getTaskType());
        subTaskModel.setDescription(subTaskDTO.getDescription());
        subTaskModel.setLastDate(subTaskDTO.getLastDate());
        subTaskModel.setStatus(StatusEnum.ACTIVE);
        subTaskModel.setParentTask(parentTaskModel);
        taskRepository.save(parentTaskModel);
        subTaskRepository.save(subTaskModel);
        return "Task added to sub task";
    }

    public String deleteSubTask(String subTask){
        Optional<SubTaskModel> subTaskModelOptional = subTaskRepository.findByTaskName(subTask);
        if (subTaskModelOptional.isEmpty()) return "Subtask not found";
        SubTaskModel subTaskModel = subTaskModelOptional.get();
        subTaskModel.setStatus(StatusEnum.INACTIVE);
        subTaskModel.setParentTask(null);
        subTaskRepository.save(subTaskModel);
        return "Subtask deleted from sub task";
    }

    public String updateSubTask(SubTaskDTO updatedSubTask, String subTask){
        Optional<SubTaskModel> subTaskModelOptional = subTaskRepository.findByTaskName(subTask);
        if (subTaskModelOptional.isEmpty()) return "Subtask not found";
        SubTaskModel subTaskModel = subTaskModelOptional.get();
        if (subTaskModel.getTaskName() != null) subTaskModel.setTaskName(updatedSubTask.getTaskName());
        if (subTaskModel.getTaskType() != null) subTaskModel.setTaskType(updatedSubTask.getTaskType());
        if (subTaskModel.getDescription() != null) subTaskModel.setDescription(updatedSubTask.getDescription());
        if (subTaskModel.getLastDate() != null) subTaskModel.setLastDate(updatedSubTask.getLastDate());
        subTaskModel.setStatus(subTaskModel.getStatus());
        subTaskModel.setParentTask(subTaskModel.getParentTask());

        subTaskRepository.save(subTaskModel);
        return "Subtask updated.";
    }

}
