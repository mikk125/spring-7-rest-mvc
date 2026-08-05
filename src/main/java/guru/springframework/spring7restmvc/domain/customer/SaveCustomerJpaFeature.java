package guru.springframework.spring7restmvc.domain.customer;

import guru.springframework.spring6restmvcapi.model.CustomerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaveCustomerJpaFeature {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerDTO execute(CustomerDTO customer) {
        return customerMapper.customerToCustomerDto(customerRepository.save(customerMapper. customerDtoToCustomer(customer)));
    }
}
