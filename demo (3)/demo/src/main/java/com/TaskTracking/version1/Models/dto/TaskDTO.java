package com.TaskTracking.version1.Models.dto;

import com.TaskTracking.version1.Enum.StatusEnum;
import com.TaskTracking.version1.Enum.TaskTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private StatusEnum status;
    private String taskName;
    private String description;
    private Date lastDate;
    private TaskTypeEnum taskType;
    private ProjectDTO projectModel;
}
