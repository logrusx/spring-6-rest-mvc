package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class CustomerController {

    public final static String CUSTOMER_PATH = "/api/v1/customer";
    public final static String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";

    private final CustomerService customerService;

    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<CustomerDTO> deleteById(@PathVariable UUID customerId) {
        if(!customerService.deleteById(customerId))
            throw new NotFoundException("Id not found");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(CUSTOMER_PATH)
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping(CUSTOMER_PATH_ID)
    public CustomerDTO getCustomerById(@PathVariable UUID customerId) {
        return customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);
    }

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity addCustomer(@RequestBody CustomerDTO customer) {
        customer = customerService.saveCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + customer.getId());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity updateCustomerById(
            @PathVariable UUID customerId,
            @RequestBody CustomerDTO customer) {
//        customer = customerService.updateCustomerById(customerId, customer);
//        if (customer != null) {
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Location", "/api/v1/customer/" + customerId);
//            return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity(HttpStatus.NOT_FOUND);
        if (customerService.updateCustomerById(customerId, customer).isEmpty())
            throw new NotFoundException("Id not found");
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity patchCustomerById(
            @PathVariable UUID customerId,
            @RequestBody CustomerDTO customer
    ) {
        if (customerService.patchCustomerByID(customerId, customer).isEmpty())
            throw new NotFoundException("Id not found");
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
