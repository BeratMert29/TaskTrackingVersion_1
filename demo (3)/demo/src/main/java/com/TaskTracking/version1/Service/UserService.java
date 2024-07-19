package com.TaskTracking.version1.Service;

import com.TaskTracking.version1.Enum.StatusEnum;
import com.TaskTracking.version1.Models.*;
import com.TaskTracking.version1.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserTasksRepository userTasksRepository;
    private final SprintRepository sprintRepository;
    private final SubTaskRepository subTaskRepository;

    @Autowired
    public UserService(SprintRepository sprintRepository, UserRepository userRepository, TaskRepository taskRepository, ProjectRepository projectRepository, UserTasksRepository userTasksRepository, SubTaskRepository subTaskRepository) {
        this.sprintRepository = sprintRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userTasksRepository = userTasksRepository;
        this.subTaskRepository = subTaskRepository;
    }

    public String addProjectToUser(String projectName, String username) {
        Optional<ProjectModel> projectModelOptional = projectRepository.findByProjectName(projectName);
        if (projectModelOptional.isEmpty()) return "Project Not Found";

        Optional<UserModel> userModelOptional = userRepository.findByUsername(username);
        if (userModelOptional.isEmpty()) return "User Not Found";

        ProjectModel projectModel = projectModelOptional.get();
        UserModel userModel = userModelOptional.get();
        userModel.setProjectModel(projectModel);

        userRepository.save(userModel);
        projectModel.getUsers().add(userModel);
        projectRepository.save(projectModel);
        return "Project added to user " + userModel.getUsername();
    }

    public String addTaskToUser(String project, String taskName, String username) {
        Optional<TaskModel> taskModelOptional = taskRepository.findByTaskName(taskName);
        Optional<UserModel> userModelOptional = userRepository.findByUsername(username);
        Optional<ProjectModel> projectModelOptional = projectRepository.findByProjectName(project);

        if (taskModelOptional.isEmpty() || projectModelOptional.isEmpty()) return "Task or Project Not Found";
        if (userModelOptional.isEmpty()) return "User Not Found";

        TaskModel taskModel = taskModelOptional.get();
        UserModel userModel = userModelOptional.get();
        ProjectModel projectModel = projectModelOptional.get();

        if (taskModel.getLastDate().before(new Date()) || projectModel.getLastDate().before(new Date())) return "Task due date is passed";
        if (!projectModel.getTaskModels().contains(taskModel)) return "Task Not Found for this project";

        UserTasksModel userTasksModel = new UserTasksModel();
        userTasksModel.setUser(userModel);
        userTasksModel.setTask(taskModel);

        userTasksRepository.save(userTasksModel);
        return "Task added to user " + userModel.getUsername();
    }

    public String deleteProjectFromUser(String projectName, String username) {
        Optional<ProjectModel> projectModelOptional = projectRepository.findByProjectName(projectName);
        Optional<UserModel> userModelOptional = userRepository.findByUsername(username);

        if (projectModelOptional.isEmpty()) return "Project Not Found";
        if (userModelOptional.isEmpty()) return "User Not Found";

        ProjectModel projectModel = projectModelOptional.get();
        UserModel userModel = userModelOptional.get();
        projectModel.getUsers().remove(userModel);
        projectRepository.save(projectModel);
        userModel.setProjectModel(null);
        userRepository.save(userModel);
        return "Project Removed from user " + userModel.getUsername();
    }

    public String deleteTaskFromUser(String taskName, String username) {
        Optional<TaskModel> taskModelOptional = taskRepository.findByTaskName(taskName);
        Optional<UserModel> userModelOptional = userRepository.findByUsername(username);

        if (taskModelOptional.isEmpty()) return "Task Not Found";
        if (userModelOptional.isEmpty()) return "User Not Found";

        TaskModel taskModel = taskModelOptional.get();
        UserModel userModel = userModelOptional.get();

        if (taskModel.getStatus().equals(StatusEnum.INACTIVE) || taskModel.getLastDate().before(new Date())) return "Task is already inactive";

        Optional<UserTasksModel> userTasksModelOptional = userTasksRepository.findByUserAndTask(userModel, taskModel);
        if (userTasksModelOptional.isEmpty()) return "Task not associated with user";

        UserTasksModel userTasksModel = userTasksModelOptional.get();
        userTasksRepository.delete(userTasksModel);

        return "Task removed from user " + userModel.getUsername();
    }

    public String addSprintToUser(String SprintName, String username) {
        Optional<SprintModel> sprintModel = sprintRepository.findBySprintName(SprintName);
        if (sprintModel.isEmpty()) return "Sprint Not Found";

        Optional<UserModel> userModelOptional = userRepository.findByUsername(username);
        if (userModelOptional.isEmpty()) return "User Not Found";

        SprintModel sprint = sprintModel.get();
        UserModel userModel = userModelOptional.get();

        userModel.setSprintModel(sprint);
        userModel.setProjectModel(sprint.getProjectModel());
        userRepository.save(userModel);
        sprintRepository.save(sprint);
        return "Sprint Added to user " + userModel.getUsername();
    }

    public String deleteSprintFromUser(String SprintName, String username) {
        Optional<SprintModel> sprintModelOptional = sprintRepository.findBySprintName(SprintName);
        Optional<UserModel> userModelOptional = userRepository.findByUsername(username);
        if (sprintModelOptional.isEmpty()) return "Sprint Not Found";
        if (userModelOptional.isEmpty()) return "User Not Found";
        SprintModel sprint = sprintModelOptional.get();
        UserModel userModel = userModelOptional.get();

        if (userModel.getSprintModel() == null || !userModel.getSprintModel().equals(sprint)) return "Sprint is not associated with this user";

        userModel.setSprintModel(null);
        sprintRepository.save(sprint);
        userRepository.save(userModel);
        return "Sprint Removed from user " + userModel.getUsername();
    }

   public String addSubTaskToUser(String SubTaskName, String username) {
      Optional<SubTaskModel> subTaskModelOptional = subTaskRepository.findByTaskName(SubTaskName);
      if (subTaskModelOptional.isEmpty()) return "SubTask Not Found";

      Optional<UserModel> userModelOptional = userRepository.findByUsername(username);
      if (userModelOptional.isEmpty()) return "User Not Found";

      SubTaskModel subTaskModel = subTaskModelOptional.get();
      UserModel userModel = userModelOptional.get();

      Optional<UserTasksModel> userTasksModelOptional = userTasksRepository.findByUserAndTask(userModel,subTaskModel.getParentTask());
       UserTasksModel userTasksModel;
       if (userTasksModelOptional.isEmpty()) {
           userTasksModel = new UserTasksModel();
           userTasksModel.setUser(userModel);
           userTasksModel.setTask(subTaskModel.getParentTask());
       }
       else{
           userTasksModel = userTasksModelOptional.get();
       }
       userTasksModel.setSubTaskModel(subTaskModel);
       userTasksRepository.save(userTasksModel);
       return "SubTask Added to user " + userModel.getUsername();
    }

    public String deleteSubTaskFromUser(String subTaskName, String username) {
        Optional<SubTaskModel> subTaskModelOptional = subTaskRepository.findByTaskName(subTaskName);
        if (subTaskModelOptional.isEmpty()) return "SubTask Not Found";
        Optional<UserModel> userModelOptional = userRepository.findByUsername(username);
        if (userModelOptional.isEmpty()) return "User Not Found";
        SubTaskModel subTaskModel = subTaskModelOptional.get();
        Optional<UserTasksModel> userTasksModelOptional = userTasksRepository.findBySubTaskModel(subTaskModel);

        if (userTasksModelOptional.isEmpty()) return "SubTask Not Found";
        UserTasksModel userTasksModel = userTasksModelOptional.get();
        userTasksModel.setSubTaskModel(null);

        return "Subtask deleted from the user "+username;
    }

}
