package guru.springframework.spring7restmvc.domain.customer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final GetCustomerByIdFeature getCustomerByIdFeature;
    private final GetAllCustomerFeature getAllCustomerFeature;
    private final SaveCustomerFeature saveCustomerFeature;
    private final UpdateCustomerByIdFeature updateCustomerByIdFeature;
    private final DeleteCustomerByIdFeature deleteCustomerByIdFeature;

    @PostMapping
    public ResponseEntity<Customer> handlePost(@RequestBody  Customer customer) {
        Customer savedCustomer = saveCustomerFeature.execute(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + savedCustomer.getId().toString());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Customer> handleDelete(@PathVariable("id") UUID id) {
        deleteCustomerByIdFeature.execute(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Customer> handlePut(@PathVariable("id") UUID id, @RequestBody Customer customer) {
        updateCustomerByIdFeature.execute(id, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> listCustomers() {
        return getAllCustomerFeature.execute();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable("id") UUID id) {
        log.debug("Get customer by id was called in constructor");

        return getCustomerByIdFeature.execute(id);
    }


}
