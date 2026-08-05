package guru.springframework.spring7restmvc.domain.customer;

import guru.springframework.spring6restmvcapi.model.CustomerDTO;
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
public class FindCustomerByIdJpaFeature {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public Optional<CustomerDTO> execute(UUID id) {
        log.debug("Finding customer by id feature was called");

        return customerRepository.findById(id).map(customerMapper::customerToCustomerDto);
    }
}
