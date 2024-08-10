package com.example.projectseminario.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HomeController.class)
public class HomeControllerTest {

 @Autowired
 private MockMvc mockMvc;
 @Test
 public void testIndex() throws Exception {
  mockMvc.perform(MockMvcRequestBuilders.get("/"))
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
          .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("")));
 }
}

