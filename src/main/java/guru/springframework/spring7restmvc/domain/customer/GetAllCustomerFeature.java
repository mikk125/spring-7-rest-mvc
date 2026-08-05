package guru.springframework.spring7restmvc.domain.customer;


import guru.springframework.spring6restmvcapi.model.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class GetAllCustomerFeature {

    private Map<UUID, CustomerDTO> customers;

    public GetAllCustomerFeature() {
        this.customers = new HashMap<>();
    }

    public List<CustomerDTO> execute() {
        CustomerDTO customer1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("Maiki")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        customers.put(customer1.getId(), customer1);

        return new ArrayList<>(customers.values());
    }
}
