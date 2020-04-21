package com.dragon3.security;

import com.dragon3.infrastructure.constants.EnableStatus;
import com.dragon3.infrastructure.util.JsonUtil;
import com.dragon3.security.util.MyPasswordEncoder;
import com.dragon3.user.model.User;
import com.dragon3.user.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("h2")
public class JwtAuthenticationControllerTest {
    MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    Filter springSecurityFilterChain;
    @Autowired
    UserRepository userRepository;
    @Before
    @Transactional
    public void initData(){
        User user = new User();
        user.setId("admin");
        user.setPassword(MyPasswordEncoder.encoder("123abc"));
        user.setEnableStatus(EnableStatus.ENABLE);
        userRepository.save(user);
    }

    @Test
    public void should_return_token() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .build();
        mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                .contentType("application/json")
                .content(JsonUtil.stringify("username", "admin", "password", "123abc")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.token").exists());
    }

    @Test //(expected = AuthenticationCredentialsNotFoundException.class)
    public void should_return_code_401() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .build();
        mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                .contentType("application/json")
                .content(JsonUtil.stringify("username", "admin", "password", "111")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(100));
    }
}
