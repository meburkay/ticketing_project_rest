package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Gender;
import com.cydeo.enums.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//TODO I don't understand exactly why we use these here.
@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    @Autowired
    private MockMvc mvc;

    //We need a manager and project object to use at the tests. And we will create them at the setUp method by using @BeforeAll annotation as static. Because of that we create the object references as static too.
    static UserDTO manager;
    static ProjectDTO project;

    static String token;
    @BeforeAll
    static void setUp() {

        token = "Bearer " + "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJKeWdOdEl6RWhxeGFpaDVJNHk0LWFQT1VmckxUd3VlNm8tZkRzQ29kdU9zIn0.eyJleHAiOjE3MDIxNTU1OTYsImlhdCI6MTcwMjE1NTI5NiwianRpIjoiYjkxNmIyNjItZTEyMS00NmVlLWI1OGEtNjhhMDYzZDRkNTNiIiwiaXNzIjoiaHR0cDovLzM1LjIwNS4yMC4yOjgwODAvYXV0aC9yZWFsbXMvY3lkZW8tZGV2IiwiYXVkIjoiYWNjb3VudCIsInN1YiI6ImRkMGM1ZGM2LTk4OWYtNDRmOS05MTk0LTc4YzJhN2MyMDJjZCIsInR5cCI6IkJlYXJlciIsImF6cCI6InRpY2tldGluZy1hcHAiLCJzZXNzaW9uX3N0YXRlIjoiNTFhZDlhZmYtZmE5NC00MzlhLWJiZDctNTg5YzIxMWMxNmVjIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjgwODAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwiZGVmYXVsdC1yb2xlcy1jeWRlby1kZXYiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7InRpY2tldGluZy1hcHAiOnsicm9sZXMiOlsiTWFuYWdlciJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJvcGVuaWQgcHJvZmlsZSBlbWFpbCIsInNpZCI6IjUxYWQ5YWZmLWZhOTQtNDM5YS1iYmQ3LTU4OWMyMTFjMTZlYyIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJvenp5IiwiZ2l2ZW5fbmFtZSI6IiIsImZhbWlseV9uYW1lIjoiIn0.QmiBiXdui03y8xMi6yiiNERx91vSAmYHgofVLeLsklwH2EkKlIIWUN4NgNuEj0Ivgxdqm14q2vxUPfonLmyJeerQVm1SiF2bMeFY0Wj-st54sUPpyViEhwOFjYAEIdLsI_NmvEmuS970iviZ5ueb3ImcNL0Ag84qUkYF_lqX1bVy2W9UjBVXM1cvGbE5BrSQH1NpKbOmuwaq704GuesVzfY_8xuT7wAkI46qHAHB_E1mdS4HWjxvh-yxH7MAsV5i2v4PZd9Tx0j-xjAr5v-1QuDoG_JhjBSD9SpCGIZfu6Tru1M6bsWni-Je218E_X7sQcKXpWq9eW8d16-0OmH2dA";

        manager = new UserDTO(2L,
                "",
                "",
                "ozzy",
                "abc1",
                "",
                true,
                "",
                new RoleDTO(2L, "Manager"),
                Gender.MALE);

        project = new ProjectDTO(
                "API Project",
                "PR001",
                manager,
                LocalDate.now(),
                LocalDate.now().plusDays(5),
                "Some details",
                Status.OPEN
        );

    }

    //Try to do operation without token test
    @Test
    void givenNoToken_getProjects() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/project"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void givenToken_getProjects() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/project")
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //Here we use jsonPath and by giving arguments we can find specific information from a json file. For the arguments there is a different syntax and the document for that syntax is here https://github.com/json-path/JsonPath.
                .andExpect(jsonPath("$.data[0].projectCode").exists())
                .andExpect(jsonPath("$.data[0].assignedManager.userName").exists())
                .andExpect(jsonPath("$.data[0].assignedManager.userName").isNotEmpty())
                .andExpect(jsonPath("$.data[0].assignedManager.userName").isString())
                .andExpect(jsonPath("$.data[0].assignedManager.userName").value("ozzy"));

    }

    @Test
    void givenToken_createProject() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/project")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        //we use a method here for giving the json body as string to the content method.
                        .content(toJsonString(project)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Project is successfully created"));

    }

    @Test
    void givenToken_updateProject() throws Exception {

        project.setProjectName("API Project-2");

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/project")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(toJsonString(project)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Project is successfully updated"));

    }

    @Test
    void givenToken_deleteProject() throws Exception {

        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/project/" + project.getProjectCode())
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Project is successfully deleted"));

    }

    //We create this method for maping object to json body then convert it to string. We use jackson library here. We create a ObjectMapper and configure it. Then use it when returning our result.
    private String toJsonString(final Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        //This means we want the time for only days not hour or minutes etc.
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //We use this for the time format. normally it is 12,2,2 by doing this we change it to 12/2/2
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(obj);
    }

}
