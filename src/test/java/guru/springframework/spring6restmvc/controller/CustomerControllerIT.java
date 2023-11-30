package guru.springframework.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.mapper.CustomerMapper;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testPatchCustomerById() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
        customerDTO.setId(null);
        customerDTO.setCreationDate(null);
        String pathecd = "Pathed";
        customerDTO.setCustomerName(pathecd);


        ResponseEntity responseEntity = customerController.patchCustomerById(customer.getId(), customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        assertThat(customerRepository.findById(customer.getId()).get().getCustomerName()).isEqualTo(pathecd);

    }

    @Test
    void testDeleteByIdNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.deleteById(UUID.randomUUID()));
    }

    @Test
    @Transactional
    void testDeleteCustomerById() {
        Customer customer = customerRepository.findAll().get(0);

        ResponseEntity responseEntity = customerController.deleteById(customer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(customerRepository.findById(customer.getId()).isEmpty());
    }

    @Test
    void testUpdateExistingCustomer() {
        Customer existing = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(existing);

        customerDTO.setId(null);
        String newName = "UPDATED";
        customerDTO.setCustomerName(newName);

        ResponseEntity responseEntity = customerController.updateCustomerById(existing.getId(), customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Customer updated = customerRepository.findById(existing.getId()).get();
        assertThat(updated.getCustomerName()).isEqualTo(newName);
    }

    @Test
    void testUpdateCustomerByIdNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.updateCustomerById(UUID.randomUUID(), CustomerDTO.builder().build()));
    }

    @Test
    void testSaveNewCustomer() {
        CustomerDTO newCustomer = CustomerDTO.builder()
                .customerName("New Customer")
                .build();

        ResponseEntity responseEntity = customerController.addCustomer(newCustomer);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String uuidString = responseEntity.getHeaders().getLocation().getPath();
        uuidString = uuidString.substring(uuidString.lastIndexOf('/') + 1);
        UUID savedUuid = UUID.fromString(uuidString);

        CustomerDTO customerDTO = customerController.getCustomerById(savedUuid);
        assertThat(customerDTO).isNotNull();
    }

    @Test
    @Transactional
    void testEmptyList() {
        customerRepository.deleteAll();
        assertThat(customerController.getAllCustomers().size()).isEqualTo(0);
    }

    @Test
    void testGetById() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerController.getCustomerById(customer.getId());
        assertThat(customerDTO).isNotNull();
    }

    @Test
    void testGetByIdNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.getCustomerById(UUID.randomUUID()));
    }

    @Test
    void testListCustomers() {
        List<CustomerDTO> customers = customerController.getAllCustomers();
        assertThat(customers.size()).isEqualTo(3);
    }

}