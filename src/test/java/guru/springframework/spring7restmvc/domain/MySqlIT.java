package guru.springframework.spring7restmvc.domain;

import guru.springframework.spring7restmvc.domain.beer.Beer;
import guru.springframework.spring7restmvc.domain.beer.BeerRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;

@Testcontainers
@SpringBootTest
@ActiveProfiles("localmysql")
public class MySqlIT {

    @Container
    @ServiceConnection
    static MySQLContainer<?> mySqlContainer = new MySQLContainer<>("mysql:8.4");

//    @DynamicPropertySource
//    static void mysqlProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", mySqlContainer::getJdbcUrl);
//        registry.add("spring.datasource.username", mySqlContainer::getUsername);
//        registry.add("spring.datasource.password", mySqlContainer::getPassword);
//    }
//
//    @Autowired
//    DataSource dataSource;

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testListBeers() {
        List<Beer> beers = beerRepository.findAll();

        assertThat(beers.size()).isGreaterThan(0);
    }
}
