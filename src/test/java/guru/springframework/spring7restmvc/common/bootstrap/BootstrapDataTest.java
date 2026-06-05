package guru.springframework.spring7restmvc.common.bootstrap;

import guru.springframework.spring7restmvc.domain.beer.BeerCsvService;
import guru.springframework.spring7restmvc.domain.beer.BeerCsvServiceImpl;
import guru.springframework.spring7restmvc.domain.beer.BeerRepository;
import guru.springframework.spring7restmvc.domain.customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class BootstrapDataTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    @MockitoBean
    BeerCsvService beerCsvService;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(beerRepository, customerRepository, beerCsvService);
    }

    @Test
    void testRun() throws Exception {
        bootstrapData.run();

        assertThat(beerRepository.count()).isEqualTo(3);
        assertThat(customerRepository.count()).isEqualTo(3);
    }
}
