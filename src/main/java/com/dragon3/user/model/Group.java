package com.dragon3.user.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="group_")
@Getter @Setter
public class Group {
    @Id
    @Column(length = 32)
    private String id;
    @Column(length = 50)
    private String name;
    @Column(length = 50)
    private String description;
    @ManyToMany
    @JoinTable(name = "user_group",
            joinColumns = {@JoinColumn(name = "group_id", nullable = false, updatable = true)},
            inverseJoinColumns = {@JoinColumn(name = "user_id", nullable = false, updatable = true)}
    )
    private List<User> users = new ArrayList<>(0);
    @ManyToMany
    @JoinTable(name = "group_role",
            joinColumns = {@JoinColumn(name = "group_id", nullable = false, updatable = true)},
            inverseJoinColumns = {@JoinColumn(name = "role_id", nullable = false, updatable = true)}
    )
    private List<Role> roles = new ArrayList<>(0);
}
