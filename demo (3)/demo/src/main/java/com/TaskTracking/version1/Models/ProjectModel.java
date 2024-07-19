package com.TaskTracking.version1.Models;

import com.TaskTracking.version1.Enum.StatusEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.*;

@Entity
@Data
@Table(name = "project_table")
public class ProjectModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "project_status")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column(name = "last_date")
    private Date lastDate;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "project_description")
    private String projectDescription;

    @OneToMany(mappedBy = "projectModel", orphanRemoval = true)
    @JsonManagedReference
    private List<TaskModel> taskModels = new ArrayList<>();

    @OneToMany(mappedBy = "projectModel", orphanRemoval = true)
    @JsonManagedReference
    private List<UserModel> users = new ArrayList<>();

    @OneToMany(mappedBy = "projectModel", orphanRemoval = true)
    @JsonManagedReference
    private List<SprintModel> sprints =  new ArrayList<>();

}