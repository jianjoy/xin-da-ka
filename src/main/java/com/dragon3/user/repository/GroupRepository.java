package com.dragon3.user.repository;

import com.dragon3.user.model.Group;
import com.dragon3.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface GroupRepository extends JpaRepository<Group, String> {
}
