package com.TaskTracking.version1.Repository;

import com.TaskTracking.version1.Models.ProjectModel;
import com.TaskTracking.version1.Models.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskModel, Integer> {
     List<TaskModel> findAllByProjectModel(ProjectModel projectModel);
     Optional<TaskModel> findByTaskName(String taskName);
}
