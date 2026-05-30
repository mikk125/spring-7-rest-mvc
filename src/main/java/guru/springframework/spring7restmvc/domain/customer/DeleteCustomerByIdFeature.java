package guru.springframework.spring7restmvc.domain.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class DeleteCustomerByIdFeature {

    private Map<UUID, Customer> customers;

    public DeleteCustomerByIdFeature() {
        this.customers = new HashMap<>();
    }

    public void execute(UUID id) {
        customers.remove(id);
    }
}
