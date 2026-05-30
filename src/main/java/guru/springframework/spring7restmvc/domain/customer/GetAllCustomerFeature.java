package guru.springframework.spring7restmvc.domain.customer;


import guru.springframework.spring7restmvc.domain.beer.Beer;
import guru.springframework.spring7restmvc.domain.beer.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class GetAllCustomerFeature {

    private Map<UUID, Customer> customers;

    public GetAllCustomerFeature() {
        this.customers = new HashMap<>();
    }

    public List<Customer> execute() {
        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("Maiki")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        customers.put(customer1.getId(), customer1);

        return new ArrayList<>(customers.values());
    }
}
