package guru.springframework.spring7restmvc.domain.customer;


import guru.springframework.spring6restmvcapi.model.CustomerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetAllCustomerJpaFeature {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public List<CustomerDTO> execute() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToCustomerDto)
                .toList();
    }
}
