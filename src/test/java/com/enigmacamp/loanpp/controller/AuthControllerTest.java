package com.enigmacamp.loanpp.controller;

import com.enigmacamp.loanpp.model.dto.request.AuthRequest;
import com.enigmacamp.loanpp.model.dto.request.CustomerRequest;
import com.enigmacamp.loanpp.model.dto.response.CommonResponse;
import com.enigmacamp.loanpp.model.entity.User;
import com.enigmacamp.loanpp.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

//
//    @BeforeEach
//    void setUp() {
//        userRepository.deleteAll();
//    }

    @Test
    void registerCustomerSuccess() throws Exception {

        User user = User.builder()
                .email("tegarrrr@gmail.com")
                .password("Admin#1234")
                .build();


        CustomerRequest customerRequest = CustomerRequest.builder()
                .firstName("Tegar")
                .lastName("Ardi")
                .phone("0855555555")
                .user(user)
                .build();


        mockMvc.perform(
                post("/api/auth/signup/customer")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequest))

        ).andExpectAll(
                status().isCreated()
        ).andDo(result -> {
            CommonResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), CommonResponse.class);

            assertEquals("Register Sucess", response.getMessage());
            assertNotNull(response.getData());
        });

    }

    @Test
    void registerNonCustomerSuccess() throws Exception {

        AuthRequest authRequest = AuthRequest.builder()
                .email("tegar@gmail.com")
                .password("123445")
                .build();

        mockMvc.perform(
                post("/api/v1/auth/signup/admin")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest))

        ).andExpectAll(
                status().isCreated()
        ).andDo(result -> {
            CommonResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), CommonResponse.class);

            assertEquals("Register Sucess", response.getMessage());
            assertNotNull(response.getData());
        });

    }


//    @Test
//    void registerCustumerFailed() throws Exception {
//
//        AuthRequest authRequest = AuthRequest.builder()
//                .email("tegar@gmail.com")
//                .password("123445")
//                .build();
//
//        mockMvc.perform(
//                post("/api/v1/auth/signup/admin")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(authRequest))
//
//        ).andExpectAll(
//                status().isCreated()
//        ).andDo(result -> {
//            CommonResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), CommonResponse.class);
//
//            assertEquals("Register Sucess", response.getMessage());
//            assertNotNull(response.getData());
//        });
//
//    }

    @Test
    void loginSuccess() throws Exception {
        AuthRequest authRequest = AuthRequest.builder()
                .email("tegar@gmail.com")
                .password("123445")
                .build();

        mockMvc.perform(
                post("/api/v1/auth/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest))

        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            CommonResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), CommonResponse.class);

            assertEquals("Login Success", response.getMessage());
            assertNotNull(response.getData());
        });

    }

}