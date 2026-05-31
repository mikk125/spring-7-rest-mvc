package guru.springframework.spring7restmvc.domain.customer;

import guru.springframework.spring7restmvc.common.exception.NotFoundException;
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
    public static final String CUSTOMER_PATH_FIND = CUSTOMER_PATH_ID + "/find";

    private final GetCustomerByIdFeature getCustomerByIdFeature;
    private final FindCustomerByIdFeature findCustomerByIdFeature;
    private final GetAllCustomerFeature getAllCustomerFeature;
    private final SaveCustomerFeature saveCustomerFeature;
    private final UpdateCustomerByIdFeature updateCustomerByIdFeature;
    private final DeleteCustomerByIdFeature deleteCustomerByIdFeature;

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity<CustomerDTO> handlePost(@RequestBody CustomerDTO customer) {
        CustomerDTO savedCustomer = saveCustomerFeature.execute(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + savedCustomer.getId().toString());

        return new ResponseEntity<>(savedCustomer, headers, HttpStatus.CREATED);
    }

    @DeleteMapping(value = CUSTOMER_PATH_ID)
    public ResponseEntity<CustomerDTO> handleDelete(@PathVariable("id") UUID id) {
        deleteCustomerByIdFeature.execute(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = CUSTOMER_PATH_ID)
    public ResponseEntity<CustomerDTO> handlePut(@PathVariable("id") UUID id, @RequestBody CustomerDTO customer) {
        updateCustomerByIdFeature.execute(id, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = CUSTOMER_PATH)
    public List<CustomerDTO> listCustomers() {
        return getAllCustomerFeature.execute();
    }

    @GetMapping(CUSTOMER_PATH_FIND)
    public CustomerDTO findCustomerById(@PathVariable("id") UUID id) {
        log.debug("Find customer by id was called in constructor");

        return findCustomerByIdFeature.execute(id).orElseThrow(NotFoundException::new);
    }

    @GetMapping(value = CUSTOMER_PATH_ID)
    public CustomerDTO getCustomerById(@PathVariable("id") UUID id) {
        log.debug("Get customer by id was called in constructor");

        return getCustomerByIdFeature.execute(id);
    }


}
