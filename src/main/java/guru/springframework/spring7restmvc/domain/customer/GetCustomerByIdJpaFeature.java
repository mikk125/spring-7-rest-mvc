package guru.springframework.spring7restmvc.domain.customer;


import guru.springframework.spring7restmvc.domain.beer.Beer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetCustomerByIdJpaFeature {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerDTO execute(UUID id) {
        log.debug("Getting customer by id feature was called");

        Optional<Customer> result = customerRepository.findById(id);
        return result.map(customerMapper::customerToCustomerDto).orElse(null);
    }
}
