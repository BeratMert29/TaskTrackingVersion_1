package com.TaskTracking.version1.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_tasks")
@Data
public class UserTasksModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "task_id")
    @JsonBackReference
    private TaskModel task;

    @ManyToOne
    @JoinColumn(name = "sub_task_id")
    @JsonBackReference
    private SubTaskModel subTaskModel;

}
