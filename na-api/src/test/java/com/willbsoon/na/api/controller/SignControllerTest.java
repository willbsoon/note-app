package com.willbsoon.na.api.controller;

import com.willbsoon.na.api.entity.User;
import com.willbsoon.na.api.repo.UserJpaRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SignControllerTest {

    @Autowired
    private UserJpaRepo userJpaRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception{
        userJpaRepo.save(User.builder()
                .uid("1234").name("1234")
                .password(passwordEncoder.encode("1234"))
                .roles(Collections.singletonList("ROLE_USER")).build());
    }

    @Test
    public void signin() throws Exception{
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("uid","1234");
        params.add("password","1234");
        mockMvc.perform(post("/v1/signin").params(params))
                .andDo(result -> {})
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").exists())
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    public void signup() throws Exception{
        long epochTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("uid","1234_"+epochTime);
        params.add("password","1234");
        params.add("name","1234_"+epochTime);
        mockMvc.perform(post("/v1/signup").params(params))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").exists());
    }

//    @Test
//    @WithMockUser(username = "mockUser", roles = {"ADMIN"}) // 가상의 Mock 유저 대입
//    public void accessdenied() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders
//                .get("/v1/users"))
//                .andDo(print())
//                .andExpect(status().is(302))
//                .andExpect(forwardedUrl("/exception/accessdenied"));
//    }
}