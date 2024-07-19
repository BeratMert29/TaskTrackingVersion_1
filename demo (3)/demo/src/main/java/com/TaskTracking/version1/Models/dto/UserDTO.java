package com.TaskTracking.version1.Models.dto;

import com.TaskTracking.version1.Enum.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String username;
    private UserRoleEnum Role;
    private List<TaskDTO> Tasks;
    private List<ProjectDTO> Projects;
    private  List<SprintDTO> Sprints;
}
