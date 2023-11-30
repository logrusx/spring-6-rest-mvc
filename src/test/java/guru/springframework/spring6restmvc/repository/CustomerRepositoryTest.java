package guru.springframework.spring6restmvc.repository;

import guru.springframework.spring6restmvc.entities.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {


        customerRepository.save(Customer.builder()
                .customerName("Joro 1")
                .build());
        customerRepository.save(Customer.builder()
                .customerName("Joro 2")
                .build());
        customerRepository.save(Customer.builder()
                .customerName("Joro 3")
                .build());
    }

    @Test
    void testSaveCustomer() {
        Customer savedCustomer = customerRepository.save(Customer.builder().customerName("Joro").build());
        assertThat(savedCustomer.getId()).isNotNull();
        assertThat(savedCustomer.getCustomerName()).isEqualTo("Joro");
        assertThat(customerRepository.findAll().size()).isEqualTo(4);
    }
}