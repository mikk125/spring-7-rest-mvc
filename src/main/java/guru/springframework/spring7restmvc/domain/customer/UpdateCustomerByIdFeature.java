package guru.springframework.spring7restmvc.domain.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class UpdateCustomerByIdFeature {

    private Map<UUID, Customer> customers;

    public UpdateCustomerByIdFeature() {
        this.customers = new HashMap<>();
    }

    public void execute(UUID id, Customer customer) {
        Customer existing = customers.get(id);

        existing.setName(customer.getName());
        existing.setVersion(customer.getVersion());

        customers.put(id, existing);
    }
}
