package com.example.springjwt.controller;

import com.github.javafaker.Faker;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mvc;

    private final Faker faker = new Faker();


    // Begin of get request '/users' url tests

    @Test
    @DisplayName("Can '/users:GET' access without authentication, Expected: UnAuthorized")
    public void getUsersWithoutAuthentication() throws Exception {

        var mvcResult = mvc.perform(get("/users"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.httpStatus").value(UNAUTHORIZED.name()))
                .andExpect(jsonPath("$.message")
                        .value("Invalid username or password!"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentType())
                .isEqualTo("application/json;charset=UTF-8");
    }

    @Test
    @WithMockUser(authorities = {"ROLE_USER"})
    @DisplayName("Can '/users:GET' accessible with 'ROLE_USER', Expected Forbidden")
    public void getUsersWithUserRole() throws Exception {

        var mvcResult = mvc.perform(get("/users"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.httpStatus").value(FORBIDDEN.name()))
                .andExpect(jsonPath("$.message")
                        .value("Access to this resource on the server is denied!"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentType())
                .isEqualTo("application/json;charset=UTF-8");
    }


    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @DisplayName("Can '/users:GET' get all records with 'ROLE_ADMIN', Expected Ok")
    public void getUsers() throws Exception {

        var mvcResult = mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].id").isNumber())
                .andExpect(jsonPath("$.[0].userName").isString())
                .andExpect(jsonPath("$.[0].fullName").isString())
                .andExpect(jsonPath("$.[0].email").isString())
                .andExpect(jsonPath("$.[0].createdDate").isString())
                .andExpect(jsonPath("$.[0].roles").isArray())
                .andExpect(jsonPath("$.[0].roles[0].id").isNumber())
                .andExpect(jsonPath("$.[0].roles[0].name").isString())
                .andReturn();


        assertThat(mvcResult.getResponse().getContentType())
                .isEqualTo("application/json;charset=UTF-8");
    }

    // End of get request '/users' url tests


    // Begin of post request '/users' url tests


    @Test
    @DisplayName("Empty values for '/users:POST', Expected BAD_REQUEST")
    public void saveUsersEmptyValues() throws Exception {

        var requestBody = new JSONObject();

        requestBody.put("userName", "");
        requestBody.put("fullName", "");
        requestBody.put("email", "");
        requestBody.put("password", "");


        var request = post("/users")
                .content(requestBody.toString())
                .contentType(APPLICATION_JSON);


        var mvcResult = mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation Error"))
                .andExpect(jsonPath("$.httpStatus").value(BAD_REQUEST.name()))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.errors.userName").value("must not be empty"))
                .andExpect(jsonPath("$.errors.fullName").value("must not be empty"))
                .andExpect(jsonPath("$.errors.email").value("must not be empty"))
                .andExpect(jsonPath("$.errors.password").value("must not be empty"))
                .andReturn();

        assertThat(mvcResult.getResponse().getContentType())
                .isEqualTo("application/json;charset=UTF-8");
    }

    @Test
    @DisplayName("Overflow values for '/users:POST', Expected BAD_REQUEST")
    public void saveUsersOverflowValues() throws Exception {

        var requestBody = new JSONObject();

        requestBody.put("userName", faker.lorem().fixedString(65));
        requestBody.put("fullName", faker.lorem().fixedString(65));
        requestBody.put("email", faker.lorem().fixedString(256));


        var request = post("/users")
                .content(requestBody.toString())
                .contentType(APPLICATION_JSON);


        var mvcResult = mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation Error"))
                .andExpect(jsonPath("$.httpStatus").value(BAD_REQUEST.name()))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.errors.userName").value("size must be between 0 and 64"))
                .andExpect(jsonPath("$.errors.fullName").value("size must be between 0 and 64"))
                .andExpect(jsonPath("$.errors.email").value("size must be between 0 and 255"))
                .andReturn();

        assertThat(mvcResult.getResponse().getContentType())
                .isEqualTo("application/json;charset=UTF-8");
    }

    @Test
    @DisplayName("Can '/users:POST' save user, Expected Forbidden")
    public void saveUser() throws Exception {

        var requestBody = new JSONObject();

        requestBody.put("userName", faker.name().username());
        requestBody.put("fullName", faker.name().fullName());
        requestBody.put("email", faker.bothify("????@test.com"));
        requestBody.put("password", faker.lorem().fixedString(24));


        var request = post("/users")
                .content(requestBody.toString())
                .contentType(APPLICATION_JSON);


        mvc.perform(request)
                .andExpect(status().isCreated());

    }

}
