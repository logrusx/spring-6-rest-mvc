package guru.springframework.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.service.CustomerService;
import guru.springframework.spring6restmvc.service.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomerService customerService;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<CustomerDTO> customerArgumentCaptor;
    CustomerServiceImpl customerServiceImpl;

    @BeforeEach
    void setUp() {
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void testPatchCustomer() throws Exception {
        CustomerDTO testCustomer = customerServiceImpl.getAllCustomers().get(0);

        Map<String, Object> custMap = new HashMap<>();
        custMap.put("customerName", "New Name");

        given(customerService.patchCustomerByID(any(UUID.class), any(CustomerDTO.class))).willReturn(Optional.of(testCustomer));

        mockMvc.perform(patch(CustomerController.CUSTOMER_PATH_ID, testCustomer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(custMap)))
                .andExpect(status().isNoContent());

        verify(customerService).patchCustomerByID(uuidArgumentCaptor.capture(), customerArgumentCaptor.capture());
        assertThat(custMap.get("customerName")).isEqualTo(customerArgumentCaptor.getValue().getCustomerName());
        assertThat(testCustomer.getId()).isEqualTo(uuidArgumentCaptor.getValue());

    }

    @Test
    void testDeleteCustomer() throws Exception {
        CustomerDTO testCustomer = customerServiceImpl.getAllCustomers().get(0);

        given(customerService.deleteById(any(UUID.class))).willReturn(true);
        mockMvc.perform(delete(CustomerController.CUSTOMER_PATH_ID, testCustomer.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());


        verify(customerService).deleteById(uuidArgumentCaptor.capture());

        assertThat(testCustomer.getId()).isEqualTo(uuidArgumentCaptor.getValue());

    }

    @Test
    void testUpdateCustomer() throws Exception {
        CustomerDTO testCustomer = customerServiceImpl.getAllCustomers().get(0);

        given(customerService.updateCustomerById(any(UUID.class), any(CustomerDTO.class))).willReturn(Optional.of(testCustomer));
        mockMvc.perform(put(CustomerController.CUSTOMER_PATH_ID, testCustomer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(status().isNoContent());

        verify(customerService).updateCustomerById(uuidArgumentCaptor.capture(), any(CustomerDTO.class));
        assertThat(testCustomer.getId()).isEqualTo(uuidArgumentCaptor.getValue());

    }

    @Test
    void testAddCustomer() throws Exception {

        CustomerDTO testCustomer = customerServiceImpl.getAllCustomers().get(0);
        testCustomer.setId(null);
        testCustomer.setCreationDate(null);
        testCustomer.setCustomerName("New Customer 1");

        given(customerService.saveCustomer(any(CustomerDTO.class))).willReturn(customerServiceImpl.getAllCustomers().get(1));

        mockMvc.perform(post(CustomerController.CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(testCustomer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

    }

    @Test
    void getAllCustomers() throws Exception {
        given(customerService.getAllCustomers()).willReturn(customerServiceImpl.getAllCustomers());
        mockMvc.perform(get(CustomerController.CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(customerServiceImpl.getAllCustomers().size())));
    }

    @Test
    void getCustomerById() throws Exception {
        CustomerDTO testCustomer = customerServiceImpl.getAllCustomers().get(0);
        given(customerService.getCustomerById(testCustomer.getId())).willReturn(Optional.of(testCustomer));
        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, testCustomer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testCustomer.getId().toString())));
    }

    @Test
    void testGetCustomerByIdNotFound() throws Exception {
        given(customerService.getCustomerById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}