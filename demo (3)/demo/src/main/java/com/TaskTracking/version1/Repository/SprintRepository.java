package com.TaskTracking.version1.Repository;

import com.TaskTracking.version1.Models.ProjectModel;
import com.TaskTracking.version1.Models.SprintModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SprintRepository extends JpaRepository<SprintModel,Integer> {
    Optional<SprintModel> findByProjectModel(ProjectModel projectModel);
    Optional<SprintModel> findBySprintName(String sprintName);
}
