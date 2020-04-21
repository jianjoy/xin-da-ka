package com.dragon3.user.repository;

import com.dragon3.user.model.Role;
import com.dragon3.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, String> {
    Set<Role> findByGroupsUsersId(String id);
}
