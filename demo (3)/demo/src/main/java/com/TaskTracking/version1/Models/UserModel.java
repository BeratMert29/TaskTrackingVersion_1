package com.TaskTracking.version1.Models;

import com.TaskTracking.version1.Enum.UserRoleEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users_table")
@Data
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRoleEnum role;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<UserTasksModel> userTasks = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonBackReference
    private ProjectModel projectModel;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "sprint_id")
    private SprintModel sprintModel;
}
