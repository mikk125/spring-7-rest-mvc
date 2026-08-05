package guru.springframework.spring7restmvc.domain.customer;


import guru.springframework.spring6restmvcapi.model.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class GetCustomerByIdFeature {

    private Map<UUID, CustomerDTO> customers;

    public GetCustomerByIdFeature() {
        this.customers = new HashMap<>();
    }

    public CustomerDTO execute(UUID id) {
        return customers.get(id);
    }
}
