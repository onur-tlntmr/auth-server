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

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class RoleControllerTests {

    @Autowired
    private MockMvc mvc;

    private final Faker faker = new Faker();


    // Begin of get request '/roles' url tests


    @Test
    @DisplayName("Can '/roles:GET' access without authentication, Expected: UnAuthorized")
    public void getRolesWithoutAuthentication() throws Exception {

        var mvcResult = mvc.perform(get("/roles"))
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
    @DisplayName("Can '/roles:GET' accessible with 'ROLE_USER', Expected Forbidden")
    public void getRolesWithUserRole() throws Exception {

        var mvcResult = mvc.perform(get("/roles"))
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
    @DisplayName("Can '/roles:GET' get all records with 'ROLE_ADMIN', Expected Ok")
    public void getRoles() throws Exception {

        var mvcResult = mvc.perform(get("/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].id").isNumber())
                .andExpect(jsonPath("$.[0].name").isString())
                .andReturn();


        assertThat(mvcResult.getResponse().getContentType())
                .isEqualTo("application/json;charset=UTF-8");
    }

    // End of get request '/roles' url tests


    // Begin of post request '/roles' url tests

    @Test
    @DisplayName("Can '/roles:POST' access without authentication, Expected: UnAuthorized")
    public void saveRoleWithoutAuthentication() throws Exception {

        var mvcResult = mvc.perform(post("/roles"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.httpStatus").value(UNAUTHORIZED.name()))
                .andExpect(jsonPath("$.message").value("Invalid username or password!"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentType())
                .isEqualTo("application/json;charset=UTF-8");
    }

    @Test
    @WithMockUser(authorities = {"ROLE_USER"})
    @DisplayName("Can '/roles:POST' accessible with 'ROLE_USER', Expected Forbidden")
    public void saveRoleWithUserRole() throws Exception {

        var mvcResult = mvc.perform(post("/roles"))
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
    @DisplayName("Empty values for '/roles:POST' request, Expected BadRequest")
    public void saveRoleNullValues() throws Exception {

        // For empty value
        var requestBody = Map.of("name", "");

        var request = post("/roles")
                .content(new JSONObject(requestBody).toString())
                .contentType(APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation Error"))
                .andExpect(jsonPath("$.httpStatus").value(BAD_REQUEST.name()))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.errors.name").value("must not be empty"));

    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @DisplayName("Overflow values for '/roles:POST' request, Expected BadRequest")
    public void saveRoleOverflowValues() throws Exception {

        var overFlowStr = faker.lorem().fixedString(25);


        var requestBody = Map.of("name", overFlowStr);


        var request = post("/roles")
                .content(new JSONObject(requestBody).toString())
                .contentType(APPLICATION_JSON);


        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation Error"))
                .andExpect(jsonPath("$.httpStatus").value(BAD_REQUEST.name()))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.errors.name")
                        .value("size must be between 0 and 24"));
    }


    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @DisplayName("Can '/roles:POST' save role, Expected Ok")
    public void saveRole() throws Exception {

        var requestBody = Map.of("name", "ROLE_TEST");

        var request = post("/roles")
                .content(new JSONObject(requestBody).toString())
                .contentType(APPLICATION_JSON);


        mvc.perform(request)
                .andExpect(status().isCreated());
    }

    // End of post request '/roles' url tests


    // Begin of post request 'roles/addtouser'

    @Test
    @DisplayName("Can '/roles/addtouser:POST' access without authentication, Expected: UnAuthorized")
    public void addRoleToUserWithoutAuthentication() throws Exception {

        var mvcResult = mvc.perform(post("/roles/addtouser"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.httpStatus").value(UNAUTHORIZED.name()))
                .andExpect(jsonPath("$.message").value("Invalid username or password!"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentType())
                .isEqualTo("application/json;charset=UTF-8");
    }

    @Test
    @WithMockUser(authorities = {"ROLE_USER"})
    @DisplayName("Can '/roles/addtouser:POST' accessible with 'ROLE_USER', Expected Forbidden")
    public void addRoleToUserAccessWithUserRole() throws Exception {

        var mvcResult = mvc.perform(post("/roles/addtouser"))
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
    @DisplayName("Invalid requests for '/roles/addtouser:POST', Expected BAD_REQUESTS")
    public void addRoleToUserNullValues() throws Exception {

        var requestBody = Map.of("userName", "", "roleName", "");

        var request = post("/roles/addtouser")
                .content(new JSONObject(requestBody).toString())
                .contentType(APPLICATION_JSON);


        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation Error"))
                .andExpect(jsonPath("$.httpStatus").value(BAD_REQUEST.name()))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.errors.userName").value("must not be empty"))
                .andExpect(jsonPath("$.errors.roleName").value("must not be empty"));

    }


    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @DisplayName("Invalid requests for '/roles/addtouser:POST', Expected BAD_REQUESTS")
    public void addRoleToUserOverflowValues() throws Exception {

        var overFlowStr = faker.lorem().fixedString(65);

        var requestBody = Map.of("userName", overFlowStr, "roleName", overFlowStr);

        var request = post("/roles/addtouser")
                .content(new JSONObject(requestBody).toString())
                .contentType(APPLICATION_JSON);


        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation Error"))
                .andExpect(jsonPath("$.httpStatus").value(BAD_REQUEST.name()))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.errors.roleName")
                        .value("size must be between 0 and 24"))
                .andExpect(jsonPath("$.errors.userName")
                        .value("size must be between 0 and 64"));

    }


    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @DisplayName("Can '/roles/addtouser:POST' save role to user, Expected Ok")
    public void addRoleToUser() throws Exception {

        var requestBody = Map.of("userName", "user", "roleName", "ROLE_TEST");

        var request = post("/roles/addtouser")
                .content(new JSONObject(requestBody).toString())
                .contentType(APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk());
    }

    // End of post request 'roles/addtouser'
}
