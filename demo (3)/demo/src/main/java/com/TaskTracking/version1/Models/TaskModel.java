package com.TaskTracking.version1.Models;

import com.TaskTracking.version1.Enum.StatusEnum;
import com.TaskTracking.version1.Enum.TaskTypeEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tasks")
@Data
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonBackReference
    private ProjectModel projectModel;

    @ManyToOne
    @JoinColumn(name = "sprint_id")
    @JsonBackReference
    private SprintModel sprintModel;

    @OneToMany(mappedBy = "task")
    @JsonManagedReference
    private List<UserTasksModel> userTasks = new ArrayList<>();

    @OneToMany(mappedBy = "parentTask", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SubTaskModel> subTaskModels = new ArrayList<>();
}
