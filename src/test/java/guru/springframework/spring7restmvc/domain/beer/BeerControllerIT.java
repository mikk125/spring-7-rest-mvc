package guru.springframework.spring7restmvc.domain.beer;

import guru.springframework.spring6restmvcapi.model.BeerDTO;
import guru.springframework.spring7restmvc.common.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BeerControllerIT {

    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;
    @Autowired
    private BeerMapper beerMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void testListBeersByName() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH).queryParam("beerName", "IPA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2413)));
    }

    @Test
    void deleteByIdNotFoundTest() {
        assertThrows(NotFoundException.class, () -> beerController.handleDelete(UUID.randomUUID()));
    }

    @Rollback
    @Transactional
    @Test
    void deleteByIdTest() {
        Beer beer = beerRepository.findAll().get(0);

        ResponseEntity responseEntity = beerController.handleDelete(beer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(beerRepository.findById(beer.getId())).isEmpty();
    }

    @Test
    void updateNotFoundTest() {
        assertThrows(NotFoundException.class, () -> beerController.handlePut(UUID.randomUUID(), BeerDTO.builder().build()));
    }

    @Test
    void updateExistingBeerTest() {
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerMapper.beerToBeerDto(beer);
        beerDTO.setId(null);
        beerDTO.setVersion(null);
        final String beerName = "Saku";
        beerDTO.setBeerName(beerName);

        ResponseEntity responseEntity = beerController.handlePut(beer.getId(), beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Beer updatedBeer = beerRepository.findById(beer.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);
    }

    @Rollback
    @Transactional
    @Test
    void saveNewBeerTest() {
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("New beer")
                .build();

        ResponseEntity responseEntity = beerController.handlePost(beerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Beer beer = beerRepository.findById(savedUUID).get();
        assertThat(beer).isNotNull();
    }

    @Test
    void testBeerIdNotFound() {
        assertNull(beerController.getBeerById(UUID.randomUUID()));

        beerController.getBeerById(UUID.randomUUID());
    }

    @Test
    void testGetById() {
        Beer beer = beerRepository.findAll().get(0);

        BeerDTO dto = beerController.getBeerById(beer.getId());
        assertThat(dto).isNotNull();
    }

    @Test
    void testListBeers() {
        Page<BeerDTO> dtos = beerController.listBeers(any(), any(), any(), any());

        assertThat(dtos.stream().count()).isEqualTo(2413);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        beerRepository.deleteAll();

        Page<BeerDTO> dtos = beerController.listBeers(any(), any(), any(), any());

        assertThat(dtos.getContent().size()).isEqualTo(0);
    }
}
