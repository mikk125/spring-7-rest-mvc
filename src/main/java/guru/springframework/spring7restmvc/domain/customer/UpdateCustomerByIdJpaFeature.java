package guru.springframework.spring7restmvc.domain.customer;

import guru.springframework.spring6restmvcapi.model.CustomerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateCustomerByIdJpaFeature {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    
    public Optional<CustomerDTO> execute(UUID id, CustomerDTO customer) {
        AtomicReference<Optional<CustomerDTO>> result = new AtomicReference<>();

        customerRepository.findById(id).ifPresentOrElse(fC -> {
            fC.setName(customer.getName());
            result.set(Optional.of(customerMapper.customerToCustomerDto(customerRepository.save(fC))));
        }, () -> result.set(Optional.empty()));

        return result.get();
    }
}
