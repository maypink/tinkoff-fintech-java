package com.maypink.tinkoff.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class SecurityTests extends SpringBootApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void roleUserGetAllExpect200() throws Exception {
        mockMvc.perform(get("/weather/all"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "userAdmin", password = "password", roles = "ADMIN")
    public void roleAdminGetAllExpect200() throws Exception {
        mockMvc.perform(get("/weather/all"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void roleUserGetNameExpect200() throws Exception {
        mockMvc.perform(get("/weather/Milan"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "userAdmin", password = "password", roles = "ADMIN")
    public void roleAdminGetNameExpect200() throws Exception {
        mockMvc.perform(get("/weather/Milan"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void roleUserPostNewExpect403() throws Exception {
        String weatherName = "Milan";
        mockMvc.perform(post("/weather/new").contentType(MediaType.APPLICATION_JSON)
                        .content(weatherName)
                        .characterEncoding("utf-8"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "userAdmin", password = "password", roles = "ADMIN")
    public void roleUserPostNewExpect201() throws Exception {
        String weatherName = "Milan";
        mockMvc.perform(post("/weather/new").contentType(MediaType.APPLICATION_JSON)
                        .content(weatherName)
                        .characterEncoding("utf-8"))
                .andExpect(status().isCreated());
    }

}
