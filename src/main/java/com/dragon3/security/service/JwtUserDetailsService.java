package com.dragon3.security.service;

import com.dragon3.infrastructure.constants.EnableStatus;
import com.dragon3.user.model.Role;
import com.dragon3.user.model.User;
import com.dragon3.user.repository.RoleRepository;
import com.dragon3.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(username).map(user -> {
            return new org.springframework.security.core.userdetails.User(user.getId(), user.getPassword(),
                    user.getEnableStatus()== EnableStatus.ENABLE, true, true, true, getAuthorities(user));
        }).orElseThrow(() -> new UsernameNotFoundException("User details not found with this id: " + username));
    }

    private Set<Role> getAuthorities(User user) {
        Set<Role> authorities = roleRepository.findByGroupsUsersId(user.getId());
        return authorities;
    }
}
