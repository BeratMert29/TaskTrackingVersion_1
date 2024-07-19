package com.TaskTracking.version1.Models.dto;

import com.TaskTracking.version1.Enum.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SprintDTO {
    private Date date;
    private String description;
    private String sprintName;
    private StatusEnum statusEnum;
}
