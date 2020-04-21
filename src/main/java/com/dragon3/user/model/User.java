package com.dragon3.user.model;

import com.dragon3.infrastructure.constants.EnableStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
public class User {
    @Id
    @Column(length = 32)
    private String id;
    @Column(length = 50)
    private String name;
    @JsonIgnore
    @Column(length = 100)
    private String password;
    @Column(length = 11)
    private String mobile;
    @Column(length = 50)
    private String photo;
    private Date registerTime;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EnableStatus enableStatus;
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "user_group",
            joinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = true) },
            inverseJoinColumns = { @JoinColumn(name = "group_id", nullable = false, updatable = true) }
    )
    private List<Group> groups = new ArrayList<>(0);
}
