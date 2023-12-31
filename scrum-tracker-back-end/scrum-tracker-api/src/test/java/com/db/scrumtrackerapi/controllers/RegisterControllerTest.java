package com.db.scrumtrackerapi.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.db.scrumtrackerapi.controller.RegisterCustomerController;
import com.db.scrumtrackerapi.model.Customer;
import com.db.scrumtrackerapi.model.dto.CustomerDTO;
import com.db.scrumtrackerapi.model.enums.Role;
import com.db.scrumtrackerapi.model.view.CustomerView;
import com.db.scrumtrackerapi.model.view.ErrorMessageView;
import com.db.scrumtrackerapi.services.impl.CustomerService;
import com.db.scrumtrackerapi.util.CustomerDTOSerializer;
import com.db.scrumtrackerapi.util.ErrorMessageSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class RegisterControllerTest {
    
    @Autowired
    @InjectMocks
    private RegisterCustomerController registerCustomerController;

    @Mock
    private CustomerService customerService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private HttpHeaders headers;

    @BeforeAll
    public void setUp() throws JsonProcessingException {

        CustomerDTOSerializer customerDTOSerializer= new CustomerDTOSerializer();
        ErrorMessageSerializer errorMessageSerializer = new ErrorMessageSerializer();
        SimpleModule customerDtoSerializerModule = new SimpleModule().addSerializer(CustomerDTO.class, customerDTOSerializer);
        SimpleModule errorMessageSerializerModule = new SimpleModule().addSerializer(ErrorMessageView.class, errorMessageSerializer);
        
        objectMapper = new ObjectMapper()
                            .registerModule(customerDtoSerializerModule)
                            .registerModule(errorMessageSerializerModule);
        
        mockMvc = MockMvcBuilders.standaloneSetup(registerCustomerController).build();
        
        headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
   
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testRegisterCustomer() throws Exception {       
        CustomerView responseBody = new CustomerView( "Joao", "Ninguem", "joao@email.com", "ADMIN");
        String responseBodyJson = objectMapper.writeValueAsString(responseBody);

        CustomerDTO requestBody = new CustomerDTO( "Joao", "Ninguem", "joao@email.com", "Pass@2023", Role.ADMIN);
        String requestBodyJson = objectMapper.writeValueAsString(requestBody);

        Customer expectedCustomer = requestBody.toCustomer(passwordEncoder);
        
        when(customerService.save(any(Customer.class))).thenReturn(expectedCustomer);
       
        mockMvc.perform(post("/register").headers(headers).content(requestBodyJson))
        .andExpect(status().isCreated())
        .andExpect(content().json(responseBodyJson)).andReturn();

         verify(customerService).save(any(Customer.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void tiRegisterCustomerWithWeakPassword() throws Exception {
        
        CustomerDTO requestBody = new CustomerDTO( "Joao", "Ninguem", "joao@email.com", "pass123", Role.ADMIN);
        String requestBodyJson = objectMapper.writeValueAsString(requestBody);
        
        mockMvc.perform(post("/register").headers(headers).content(requestBodyJson))
        .andExpect(status().isBadRequest()).andReturn();
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void tiRegisterCustomerWithBlankPassword() throws Exception {
        
        CustomerDTO requestBody = new CustomerDTO( "Joao", "Ninguem", "joao@email.com", "", Role.ADMIN);
        String requestBodyJson = objectMapper.writeValueAsString(requestBody);
               
        mockMvc.perform(post("/register").headers(headers).content(requestBodyJson))
        .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void tiRegisterCustomerWithBlankEmail() throws Exception {
        
        CustomerDTO requestBody = new CustomerDTO( "Joao", "Ninguem", "", "Pass@2023", Role.ADMIN);
        String requestBodyJson = objectMapper.writeValueAsString(requestBody);
               
        mockMvc.perform(post("/register").headers(headers).content(requestBodyJson))
        .andExpect(status().isBadRequest()).andReturn();
    }

    // @Test
    // @WithMockUser(roles = "ADMIN")
    // void testRegisterCustomerWithNullPassword() throws Exception {
    //     headers = new HttpHeaders();
    //     headers.set("Content-Type", "application/json");
        
    //     CustomerDTO requestBody = new CustomerDTO( "Joao", "Ninguem", "joao@email.com", null, Role.ADMIN);
    //     String requestBodyJson = objectMapper.writeValueAsString(requestBody);
               
    //     mockMvc.perform(post("/register").headers(headers).content(requestBodyJson))
    //     .andExpect(status().isBadRequest()).andReturn();
    // }

    @Test
    @WithMockUser(roles = "ADMIN")
    void tiRegisterCustomerWithNullEmail() throws Exception {
        
        CustomerDTO requestBody = new CustomerDTO( "Joao", "Ninguem", null, "Pass@2023", Role.ADMIN);
        String requestBodyJson = objectMapper.writeValueAsString(requestBody);
               
        mockMvc.perform(post("/register").headers(headers).content(requestBodyJson))
        .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void tiRegisterCustomerWithBlankLastName() throws Exception {
        
        CustomerDTO requestBody = new CustomerDTO( "Joao", "", "joao@email.com", "Pass@2023", Role.ADMIN);
        String requestBodyJson = objectMapper.writeValueAsString(requestBody);
               
        mockMvc.perform(post("/register").headers(headers).content(requestBodyJson))
        .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void tiRegisterCustomerWithBlankName() throws Exception {
        
        CustomerDTO requestBody = new CustomerDTO( "", "Ninguem", "joao@email.com", "Pass@2023", Role.ADMIN);
        String requestBodyJson = objectMapper.writeValueAsString(requestBody);
               
        mockMvc.perform(post("/register").headers(headers).content(requestBodyJson))
        .andExpect(status().isBadRequest()).andReturn();
    }

}
