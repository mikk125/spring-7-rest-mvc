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
public class CustomerController {

    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_PATH_ID = "/api/v1/customer" + "/{id}";

    private final GetCustomerByIdFeature getCustomerByIdFeature;
    private final GetAllCustomerFeature getAllCustomerFeature;
    private final SaveCustomerFeature saveCustomerFeature;
    private final UpdateCustomerByIdFeature updateCustomerByIdFeature;
    private final DeleteCustomerByIdFeature deleteCustomerByIdFeature;

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity<Customer> handlePost(@RequestBody  Customer customer) {
        Customer savedCustomer = saveCustomerFeature.execute(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + savedCustomer.getId().toString());

        return new ResponseEntity<>(savedCustomer, headers, HttpStatus.CREATED);
    }

    @DeleteMapping(value = CUSTOMER_PATH_ID)
    public ResponseEntity<Customer> handleDelete(@PathVariable("id") UUID id) {
        deleteCustomerByIdFeature.execute(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = CUSTOMER_PATH_ID)
    public ResponseEntity<Customer> handlePut(@PathVariable("id") UUID id, @RequestBody Customer customer) {
        updateCustomerByIdFeature.execute(id, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = CUSTOMER_PATH)
    public List<Customer> listCustomers() {
        return getAllCustomerFeature.execute();
    }

    @GetMapping(value = CUSTOMER_PATH_ID)
    public Customer getCustomerById(@PathVariable("id") UUID id) {
        log.debug("Get customer by id was called in constructor");

        return getCustomerByIdFeature.execute(id);
    }


}
