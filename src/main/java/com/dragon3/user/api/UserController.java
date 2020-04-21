package com.dragon3.user.api;

import com.dragon3.infrastructure.exception.MobileAlreadyExistException;
import com.dragon3.infrastructure.exception.RestfulException;
import com.dragon3.infrastructure.model.RestResponse;
import com.dragon3.security.config.JwtTokenUtils;
import com.dragon3.user.model.User;
import com.dragon3.user.repository.UserRepository;
import com.dragon3.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    EntityManager em;
    @Autowired
    UserService userService;
    @Autowired
    JwtTokenUtils jwtTokenUtils;


    @PreAuthorize("hasRole('admin')")
    @GetMapping("/{id}")
    public RestResponse get(@PathVariable String id){
        User user = userRepository.findById(id).get();
        return RestResponse.data(user);
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping
    public RestResponse getSelf(){
        User user = userRepository.findById(jwtTokenUtils.getUsername()).get();
        return RestResponse.data(user);
    }

    @PutMapping
    public RestResponse put(@RequestBody User user){
        try {
            userService.create(user);
            return RestResponse.ok();
        } catch (MobileAlreadyExistException e) {
            return RestResponse.error(e);
        }
    }
}
