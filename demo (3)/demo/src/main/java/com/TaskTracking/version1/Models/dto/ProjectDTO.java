package com.TaskTracking.version1.Models.dto;

import com.TaskTracking.version1.Enum.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    private StatusEnum status;
    private String taskName;
    private Date lastDate;
    private Date sprintDate;
    private String projectName;
    private String projectDescription;
}
