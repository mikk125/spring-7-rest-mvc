package guru.springframework.spring7restmvc.domain.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class SaveCustomerFeature {

    private Map<UUID, CustomerDTO> customers;

    public SaveCustomerFeature() {
        this.customers = new HashMap<>();
    }

    public CustomerDTO execute(CustomerDTO customer) {
        CustomerDTO savedCustomer = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .version(customer.getVersion())
                .name(customer.getName())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        log.debug("Saving customer feature was called");

        customers.put(savedCustomer.getId(), savedCustomer);

        return savedCustomer;
    }
}
