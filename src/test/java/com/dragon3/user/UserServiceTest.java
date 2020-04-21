package com.dragon3.user;

import com.dragon3.Application;
import com.dragon3.infrastructure.exception.MobileAlreadyExistException;
import com.dragon3.user.model.User;
import com.dragon3.user.repository.UserRepository;
import com.dragon3.user.service.UserService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("h2")
public class UserServiceTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;


    @Test
    public void should_create_ok() throws MobileAlreadyExistException {
        String id = "user1";
        String mobile="13718111811";
        User user = new User();
        user.setId(id);
        user.setMobile(mobile);
        user.setPassword("123abc");
        userService.create(user);
        User u = userRepository.findById(id).get();
        assertThat(u).isNotNull();
        assertThat(u.getMobile()).isEqualTo(mobile);
    }

    @Test
    public void should_throw_mobile_already_exist_when_mobile_repeat() throws MobileAlreadyExistException {
        User userDefault = new User();
        userDefault.setId("default");
        userDefault.setMobile("13819191919");
        userDefault.setPassword("123abc");
        userService.create(userDefault);

        User user = new User();
        user.setId("user2");
        user.setMobile("13819191919");
        user.setPassword("123abc");
        assertThatExceptionOfType(MobileAlreadyExistException.class).isThrownBy(()->{
            userService.create(user);
        });
    }

    @Test
    public void should_throw_exception_when_mandatory_field_is_null(){
        User user = new User();
        user.setId("user3");
        assertThatExceptionOfType(Exception.class).isThrownBy(()->{
            userService.create(user);
        });
    }
}
