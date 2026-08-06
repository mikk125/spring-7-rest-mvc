package guru.springframework.spring7restmvc.common.bootstrap;

import guru.springframework.spring6restmvcapi.model.BeerStyle;
import guru.springframework.spring7restmvc.domain.beer.*;
import guru.springframework.spring7restmvc.domain.beer_order.BeerOrder;
import guru.springframework.spring7restmvc.domain.beer_order.BeerOrderLine;
import guru.springframework.spring7restmvc.domain.beer_order.BeerOrderRepository;
import guru.springframework.spring7restmvc.domain.customer.Customer;
import guru.springframework.spring7restmvc.domain.customer.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;
    private final BeerCsvService beerCsvService;
    private final BeerOrderRepository beerOrderRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCustomerData();
        loadCsvData();
        loadOrderData();
    }

    private void loadOrderData() {
        if (beerOrderRepository.count() == 0) {
            val customers = customerRepository.findAll();
            val beers = beerRepository.findAll();

            val beerIterator = beers.iterator();

            customers.forEach(customer -> {
                Beer beer1 = beerIterator.next();
                Beer beer2 = beerIterator.next();

                val beerOrder = BeerOrder.builder()
                        .customer(customer)
                        .beerOrderLines(Set.of(
                                BeerOrderLine.builder()
                                        .beer(beer1)
                                        .orderQuantity(1)
                                        .build(),
                                BeerOrderLine.builder()
                                        .beer(beer2)
                                        .orderQuantity(2)
                                        .build()
                        ))
                        .build();

                beerOrderRepository.save(beerOrder);
            });

            val orders = beerOrderRepository.findAll();
        }

    }

    private void loadCsvData() throws FileNotFoundException {
        if (beerRepository.count() <10) {
            File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");

            List<BeerCSVRecord> recs = beerCsvService.convertCSV(file);

            recs.forEach(beerCsvRecord -> {
                BeerStyle beerStyle = switch (beerCsvRecord.getStyle()) {
                    case "American Pale Lager" -> BeerStyle.LAGER;
                    case "American Pale Ale (APA)", "American Black Ale", "Belgian Dark Ale", "American Blonde Ale" -> BeerStyle.ALE;
                    case "American IPA", "American Double / Imperial IPA", "Belgian IPA" -> BeerStyle.IPA;
                    case "American Porter" -> BeerStyle.PORTER;
                    case "Oatmeal Stout", "American Sout" -> BeerStyle.STOUT;
                    case "Saison / Farmhouse Ale" -> BeerStyle.SAISON;
                    case "Fruit / Vegetable Beer", "Winter Warmer", "Berliner Weissbier" -> BeerStyle.WHEAT;
                    case "English Pale Ale" -> BeerStyle.PALE_ALE;
                    default -> BeerStyle.PILSNER;
                };

                beerRepository.save(
                        Beer.builder()
                        .beerName(StringUtils.abbreviate(beerCsvRecord.getBeer(), 50))
                        .beerStyle(beerStyle)
                        .price(BigDecimal.TEN)
                        .upc(beerCsvRecord.getRow().toString())
                        .quantityOnHand(beerCsvRecord.getCount())
                        .build());
            });
        }
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
