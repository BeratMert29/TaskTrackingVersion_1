package com.TaskTracking.version1.Repository;

import com.TaskTracking.version1.Models.SubTaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SubTaskRepository extends JpaRepository<SubTaskModel, Integer> {
    Optional<SubTaskModel> findByTaskName(String subTaskName);
}
