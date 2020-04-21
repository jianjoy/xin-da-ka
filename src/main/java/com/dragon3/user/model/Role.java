package com.dragon3.user.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Role implements Serializable, GrantedAuthority {
    @Id
    @Column(length = 32)
    private String id;
    @Column(length = 32)
    private String authority;
    @Column(length = 50)
    private String description;
    @ManyToMany
    @JoinTable(name = "group_role",
            joinColumns = {@JoinColumn(name = "role_id", nullable = false, updatable = true)},
            inverseJoinColumns = {@JoinColumn(name = "group_id", nullable = false, updatable = true)}
    )
    private List<Group> groups = new ArrayList<>(0);
}
