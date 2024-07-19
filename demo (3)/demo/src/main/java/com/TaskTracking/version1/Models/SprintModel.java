package com.TaskTracking.version1.Models;

import com.TaskTracking.version1.Enum.StatusEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sprint_table")
@Data
public class SprintModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sprintName")
    private String sprintName;

    @Enumerated(EnumType.STRING)
    @Column(name = "sprintStatus")
    private StatusEnum sprintStatus;

    @Column(name = "sprintDescription")
    private String sprintDescription;

    @Column(name = "sprint_date")
    private Date sprintDate;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @JsonBackReference
    private ProjectModel projectModel;

    @OneToMany(mappedBy = "sprintModel", orphanRemoval = true)
    @JsonManagedReference
    private List<UserModel> userModels = new ArrayList<>();

    @OneToMany(mappedBy = "sprintModel", orphanRemoval = true)
    @JsonManagedReference
    private List<TaskModel> tasks = new ArrayList<>();

}