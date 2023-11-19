package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CustomerServiceJPA implements CustomerService {
    @Override
    public List<CustomerDTO> getAllCustomers() {
        return null;
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.empty();
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customer) {
        return null;
    }

    @Override
    public CustomerDTO updateCustomerById(UUID customerId, CustomerDTO customer) {
        return null;
    }

    @Override
    public CustomerDTO deleteById(UUID customerId) {
        return null;
    }

    @Override
    public CustomerDTO patchCustomerByID(UUID customerId, CustomerDTO customer) {
        return null;
    }
}
