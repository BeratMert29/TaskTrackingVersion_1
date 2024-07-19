package com.TaskTracking.version1.Repository;

import com.TaskTracking.version1.Models.ProjectModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<ProjectModel,Integer> {
    Optional<ProjectModel> findByProjectName(String projectName);
}
