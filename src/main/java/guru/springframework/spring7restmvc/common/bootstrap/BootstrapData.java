package guru.springframework.spring7restmvc.common.bootstrap;

import guru.springframework.spring7restmvc.domain.beer.Beer;
import guru.springframework.spring7restmvc.domain.beer.BeerRepository;
import guru.springframework.spring7restmvc.domain.beer.BeerStyle;
import guru.springframework.spring7restmvc.domain.customer.Customer;
import guru.springframework.spring7restmvc.domain.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCustomerData();
    }

    private void loadBeerData() {
        if (beerRepository.count() == 0) {
            Beer beer1 = Beer.builder()
                    .beerName("Saku")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("13242")
                    .price(new java.math.BigDecimal("12.99"))
                    .quantityOnHand(122)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Rapla")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("132452")
                    .price(new java.math.BigDecimal("15.99"))
                    .quantityOnHand(515)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Läte")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("132242")
                    .price(new java.math.BigDecimal("13.99"))
                    .quantityOnHand(5434)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            beerRepository.saveAll(java.util.List.of(beer1, beer2, beer3));
        }
    }

    private void loadCustomerData() {
        if (customerRepository.count() == 0) {
            Customer customer1 = Customer.builder()
                    .name("Martin")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .name("Jaak")
                    .version(2)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Customer customer3 = Customer.builder()
                    .name("Bob")
                    .version(3)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            customerRepository.saveAll(java.util.List.of(customer1, customer2, customer3));
        }
    }
}
