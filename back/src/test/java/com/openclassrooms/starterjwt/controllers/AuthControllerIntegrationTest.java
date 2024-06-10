package com.openclassrooms.starterjwt.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.List;

import org.junit.jupiter.api.Test;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthControllerIntegrationTest {
	
	 @Autowired
	 private MockMvc mockMvc;

	 @Test
	 public void testAuthenticateUser_nullBody_shouldReturnBadRequest() throws Exception {
	      mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
	              .contentType(MediaType.APPLICATION_JSON))
	              .andExpect(MockMvcResultMatchers.status().isBadRequest());
	 }

	 @Test
	 public void testAuthenticateUser_missingPassword_shouldReturnBadRequest() throws Exception {
	     String requestBody = "{\"email\":\"test@example.com\"}";
   
	     mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(requestBody))
	              .andExpect(MockMvcResultMatchers.status().isBadRequest());
	 }
	 
	 @Test
	 public void testLogin_success() throws Exception {	 
		  LoginRequest loginRequest = new LoginRequest();
	      loginRequest.setEmail("john@email.com");
	      loginRequest.setPassword("password");

	      String jsonLoginRequest = new ObjectMapper().writeValueAsString(loginRequest);

	      mockMvc.perform(post("/api/auth/login") 
	                .contentType(MediaType.APPLICATION_JSON) 
	                .content(jsonLoginRequest)) 
	                .andExpect(status().isOk()) 
	                .andExpect(jsonPath("$.token", is(notNullValue())));
		 
	 }
	 
	 @Test
	 public void testLogin_invalidUser_shouldReturnUnauthorized() throws Exception {	 
		 LoginRequest loginRequest = new LoginRequest();
	     loginRequest.setEmail("invalidUser@test.com");
	     loginRequest.setPassword("invalidUserPwd");
	     String jsonLoginRequest = new ObjectMapper().writeValueAsString(loginRequest);

	     mockMvc.perform(post("/api/auth/login") 
	                .contentType(MediaType.APPLICATION_JSON) 
	                .content(jsonLoginRequest)) 
	                .andExpect(status().isUnauthorized()); 
		 
	 }
	 
	 @Test
	 public void testRegister_alreadyExistingUser_shouldReturnBadRequest() throws Exception {
		  SignupRequest signupRequest = new SignupRequest();
	      signupRequest.setEmail("john@email.com");
	      signupRequest.setFirstName("John");
	      signupRequest.setLastName("DOE");
	      signupRequest.setPassword("password");
	      String jsonSignupRequest = new ObjectMapper().writeValueAsString(signupRequest);
	      
	      mockMvc.perform(post("/api/auth/register")
	               .contentType(MediaType.APPLICATION_JSON)
	               .content(jsonSignupRequest)) 
	               .andExpect(status().isBadRequest())
	               .andExpect(jsonPath("$.message", is("Error: Email is already taken!")));
	 }
	 
	 @Test
	 public void testRegister_missingParameter_shouldReturnBadRequest() throws Exception {
		  SignupRequest signupRequest = new SignupRequest();
	      signupRequest.setEmail("newuser@email.com");
	      signupRequest.setFirstName("new");
	      signupRequest.setLastName("USER");
	      String jsonSignupRequest = new ObjectMapper().writeValueAsString(signupRequest);

	      
	      mockMvc.perform(post("/api/auth/register")
	               .contentType(MediaType.APPLICATION_JSON)
	               .content(jsonSignupRequest)) 
	               .andExpect(status().isBadRequest());
	 }
	 
	 @Test
	 public void testRegister_success() throws Exception {
		  SignupRequest signupRequest = new SignupRequest();
	      signupRequest.setEmail("newuser@email.com");
	      signupRequest.setFirstName("new");
	      signupRequest.setLastName("USER");
	      signupRequest.setPassword("password");
	      String jsonSignupRequest = new ObjectMapper().writeValueAsString(signupRequest);
	      
	      mockMvc.perform(post("/api/auth/register")
	               .contentType(MediaType.APPLICATION_JSON)
	               .content(jsonSignupRequest)) 
	               .andExpect(status().isOk())
	               .andExpect(jsonPath("$.message", is("User registered successfully!")));
	 }
	 
	 
	 
	 
}
