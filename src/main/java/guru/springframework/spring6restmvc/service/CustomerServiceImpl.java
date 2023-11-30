package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.model.CustomerDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final Map<UUID, CustomerDTO> customers;

    public CustomerServiceImpl() {
        customers = new HashMap<>();
        UUID id = UUID.randomUUID();
        customers.put(id, new CustomerDTO(id, "Joro1", Calendar.getInstance().getTime()));
        id = UUID.randomUUID();
        customers.put(id, new CustomerDTO(id, "Joro2", Calendar.getInstance().getTime()));
        id = UUID.randomUUID();
        customers.put(id, new CustomerDTO(id, "Joro3", Calendar.getInstance().getTime()));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customers.values().stream().toList();
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.of(customers.get(id));
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customer) {
        CustomerDTO saved = new CustomerDTO(UUID.randomUUID(), customer.getCustomerName(), Calendar.getInstance().getTime());
        customers.put(saved.getId(), saved);
        return saved;
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer) {
        CustomerDTO existing = customers.get(customerId);
        if (existing != null) {
            existing.setCustomerName(customer.getCustomerName());
        }
        return Optional.ofNullable(existing);
    }

    @Override
    public Boolean deleteById(UUID customerId) {
        return customers.remove(customerId) != null;
    }

    @Override
    public Optional<CustomerDTO> patchCustomerByID(UUID customerId, CustomerDTO customer) {
        CustomerDTO existing = customers.get(customerId);
        if (existing != null && StringUtils.hasText(customer.getCustomerName())){
            existing.setCustomerName(customer.getCustomerName());
        }
        return Optional.ofNullable(existing);
    }
}
