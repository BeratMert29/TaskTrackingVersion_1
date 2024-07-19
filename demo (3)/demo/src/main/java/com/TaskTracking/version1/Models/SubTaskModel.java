package com.TaskTracking.version1.Models;

import com.TaskTracking.version1.Enum.StatusEnum;
import com.TaskTracking.version1.Enum.TaskTypeEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;


@Entity
@Table(name = "sub_tasks")
@Data
public class SubTaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "parent_task_id")
    @JsonBackReference
    private TaskModel parentTask;

    @Column(name = "task_name")
    private String taskName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status;

    @Column(name = "description")
    private String description;

    @Column(name = "last_date")
    private Date lastDate;

    @Column(name = "task_type")
    @Enumerated(EnumType.STRING)
    private TaskTypeEnum taskType;

}
