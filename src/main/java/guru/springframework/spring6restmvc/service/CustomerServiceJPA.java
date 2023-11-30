package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.controller.NotFoundException;
import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.mapper.CustomerMapper;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@AllArgsConstructor
public class CustomerServiceJPA implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::customerToCustomerDto)
                .toList();
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return customerRepository
                .findById(id)
                .map(customerMapper::customerToCustomerDto);
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customer) {
        Customer saved = customerMapper.customerDtoToCustomer(customer);
        saved = customerRepository.save(saved);
        return customerMapper.customerToCustomerDto(saved);
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer) {
        AtomicReference<Optional<CustomerDTO>> ref = new AtomicReference<>();
        customerRepository.findById(customerId).ifPresentOrElse( existing -> {
            existing.setCustomerName(customer.getCustomerName());
            existing = customerRepository.save(existing);
            ref.set(Optional.of(customerMapper.customerToCustomerDto(existing)));
        }, () -> {throw new NotFoundException("Id not found");});
        return ref.get();
    }

    @Override
    public Boolean deleteById(UUID customerId) {
        if (!customerRepository.existsById(customerId))
            return false;
        customerRepository.deleteById(customerId);
        return true;
    }

    @Override
    public Optional<CustomerDTO> patchCustomerByID(UUID customerId, CustomerDTO customer) {
        AtomicReference<Optional<CustomerDTO>> ref = new AtomicReference<>();
        customerRepository.findById(customerId).ifPresentOrElse( existing -> {
            if (StringUtils.hasText(customer.getCustomerName()))
                existing.setCustomerName(customer.getCustomerName());
            existing = customerRepository.save(existing);
            ref.set(Optional.of(customerMapper.customerToCustomerDto(existing)));
        }, () -> {throw new NotFoundException("Id not found");});
        return ref.get();
    }
}
