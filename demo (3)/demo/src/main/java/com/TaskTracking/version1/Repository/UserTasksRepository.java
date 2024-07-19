package com.TaskTracking.version1.Repository;

import com.TaskTracking.version1.Models.SubTaskModel;
import com.TaskTracking.version1.Models.TaskModel;
import com.TaskTracking.version1.Models.UserModel;
import com.TaskTracking.version1.Models.UserTasksModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTasksRepository extends JpaRepository<UserTasksModel,Integer> {
    Optional<UserTasksModel> findByUserAndTask(UserModel userModel, TaskModel taskModel);
    Optional<UserTasksModel> findBySubTaskModel(SubTaskModel subTaskModel);
}
