package com.enigmacamp.loanpp.controller;

import com.enigmacamp.loanpp.model.dto.response.CommonResponse;
import com.enigmacamp.loanpp.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void getCustomerById() throws Exception {
        mockMvc.perform(
                get("/api/users/1a00120e-fe5c-43dd-bf92-56da80aef86c")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            CommonResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), CommonResponse.class);

            assertEquals("Success", response.getMessage());
            assertNotNull(response.getData());
        });
    }


    @Test
    void getAllCustomer() throws Exception {
        mockMvc.perform(
                get("/api/customers")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            CommonResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), CommonResponse.class);

            System.out.println(response.getData());
            assertNotNull(response.getData());
        });
    }
}