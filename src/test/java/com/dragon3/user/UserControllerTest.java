package com.dragon3.user;

import com.dragon3.infrastructure.exception.MobileAlreadyExistException;
import com.dragon3.infrastructure.model.RestResponse;
import com.dragon3.user.api.UserController;
import com.dragon3.user.model.User;
import com.dragon3.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserService userService;
    @Autowired
    UserController userController;

    @Test
    public void get() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/users/admin"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);

    }

    @Test
    public void create_user_should_return_code_ok() throws MobileAlreadyExistException {
        //given
        User user = new User();
        doNothing().when(userService).create(any()); //isA()
//        when(userService.create(any())).thenThrow(RestfulException.class);
        //when
        RestResponse response = userController.put(user);
        //then
        assertThat(response.getCode()).isEqualByComparingTo(RestResponse.HttpCode.OK);
    }

    @Test
    public void create_user_should_throw_same_mobile_error() throws MobileAlreadyExistException {
        //given
        User user = new User();
//        when(userService.create(user)).thenThrow(MobileAlreadyExistException.class);
        doThrow(MobileAlreadyExistException.class).when(userService).create(any());
        //when
        RestResponse response = userController.put(user);
        //then
        assertThat(response.getCode()).isEqualByComparingTo(RestResponse.HttpCode.MOBILE_ALREADY_EXIST);
    }


//    @Autowired
//    TestRestTemplate restTemplate;
//    @Test
//    public void greetingShouldReturnDefaultMessage() throws Exception {
//        assertThat(restTemplate.getForObject("http://localhost/users/admin",
//                RestResponse.class).getCode()).isEqualByComparingTo(RestResponse.HttpCode.OK);
//    }


}
