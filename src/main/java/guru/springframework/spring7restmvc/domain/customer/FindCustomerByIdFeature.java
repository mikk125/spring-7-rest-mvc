package guru.springframework.spring7restmvc.domain.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class FindCustomerByIdFeature {

    private Map<UUID, Customer> customers;

    public FindCustomerByIdFeature() {
        this.customers = new HashMap<>();
    }

    public Optional<Customer> execute(UUID id) {
        log.debug("Finding customer by id feature was called");

        return Optional.of(customers.get(id));
    }
}
