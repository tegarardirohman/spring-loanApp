package com.enigmacamp.loanpp.controller;

import com.enigmacamp.loanpp.constant.APIUrl;
import com.enigmacamp.loanpp.model.dto.request.AuthRequest;
import com.enigmacamp.loanpp.model.dto.request.InstalmentTypeRequest;
import com.enigmacamp.loanpp.model.dto.response.CommonResponse;
import com.enigmacamp.loanpp.model.entity.InstalmentType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apiguardian.api.API;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class InstalmentTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createInstalmentType() throws Exception {

        String type = InstalmentType.EInstalmentType.ONE_MONTH.toString();

        InstalmentTypeRequest request = InstalmentTypeRequest.builder()
                .instalmentType(type)
                .build();


        mockMvc.perform(
                post("/api/instalment-types")
                        .header("Authorization", format("Bearer %s", APIUrl.TOKEN_ADMIN))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))

        ).andExpectAll(
                status().isCreated()
        ).andDo(result -> {
            CommonResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), CommonResponse.class);

            assertNotNull(response.getData());
        });

    }

    @Test
    void getInstalmentById() throws Exception {

        String id = "554c4b6f-0876-42ea-91f0-64ad6d87da71";

        mockMvc.perform(
                get("/api/instalment-types/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            CommonResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), CommonResponse.class);

            assertNotNull(response.getData());
            System.out.println(response.getData());
        });

    }


}