package com.dragon3.security;

import com.dragon3.infrastructure.constants.EnableStatus;
import com.dragon3.infrastructure.model.RestResponse;
import com.dragon3.infrastructure.util.JsonUtil;
import com.dragon3.security.util.MyPasswordEncoder;
import com.dragon3.user.model.Group;
import com.dragon3.user.model.Role;
import com.dragon3.user.model.User;
import com.dragon3.user.repository.GroupRepository;
import com.dragon3.user.repository.RoleRepository;
import com.dragon3.user.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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
public class UrlRoleTest {
    MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    Filter springSecurityFilterChain;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    GroupRepository groupRepository;
    @Before
    @Transactional
    public void initData(){
        User user2 = new User();
        user2.setId("admin2");
        user2.setPassword(MyPasswordEncoder.encoder("123abc"));
        user2.setEnableStatus(EnableStatus.ENABLE);
        userRepository.save(user2);
        Role role = new Role();
        role.setId("admin");
        role.setAuthority("admin");
        Group group = new Group();
        group.setId("admin");
        User user = new User();
        user.setId("admin");
        user.setPassword(MyPasswordEncoder.encoder("123abc"));
        user.setEnableStatus(EnableStatus.ENABLE);
        group.getRoles().add(role);
        user.getGroups().add(group);
        roleRepository.save(role);
        groupRepository.save(group);
        userRepository.save(user);
    }

    @Test
    public void should_return_code_100() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .build();
        mockMvc.perform(MockMvcRequestBuilders.get("/users/admin"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(100));
    }

    @Test
    public void should_return_code_ok() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                .contentType("application/json")
                .content(JsonUtil.stringify("username", "admin", "password", "123abc")))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        RestResponse restResponse = JsonUtil.parse(content, RestResponse.class);
        String token = JsonUtil.getValue(JsonUtil.stringify(restResponse.getData()), "token", String.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/admin")
                .header("Authorization", "Bearer "+token))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
    }
    @Test
    public void should_return_code_102() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                .contentType("application/json")
                .content(JsonUtil.stringify("username", "admin2", "password", "123abc")))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        RestResponse restResponse = JsonUtil.parse(content, RestResponse.class);
        String token = JsonUtil.getValue(JsonUtil.stringify(restResponse.getData()), "token", String.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/admin")
                .header("Authorization", "Bearer "+token))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(102));
    }
}
