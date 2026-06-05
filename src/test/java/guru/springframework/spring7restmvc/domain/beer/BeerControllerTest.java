package guru.springframework.spring7restmvc.domain.beer;

import guru.springframework.spring7restmvc.common.exception.NotFoundException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//@SpringBootTest
@WebMvcTest(BeerController.class)
public class BeerControllerTest {

//    @Autowired
//    BeerController beerController;

    @Autowired
    MockMvc mockMvc;

//    @MockitoBean
//    GetBeerByIdFeature getBeerByIdFeature;
//
//    @MockitoBean
//    GetAllBeerFeature getAllBeerFeature;
//
//    @MockitoBean
//    SaveBeerFeature saveBeerFeature;
//
//    @MockitoBean
//    UpdateBeerByIdFeature updateBeerByIdFeature;
//
//    @MockitoBean
//    DeleteBeerByIdFeature deleteBeerByIdFeature;
//
//    @MockitoBean
//    private FindBeerByIdFeature findBeerByIdFeature;

    @MockitoBean
    GetBeerByIdJpaFeature getBeerByIdFeature;

    @MockitoBean
    GetAllBeerJpaFeature getAllBeerFeature;

    @MockitoBean
    SaveBeerJpaFeature saveBeerFeature;

    @MockitoBean
    UpdateBeerByIdJpaFeature updateBeerByIdFeature;

    @MockitoBean
    DeleteBeerByIdJpaFeature deleteBeerByIdFeature;

    @MockitoBean
    private FindBeerByIdJpaFeature findBeerByIdFeature;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private GetAllBeerJpaFeature getAllBeerJpaFeature;

    @Test
    @Disabled
    void testCreateBeer() {
        BeerDTO beer = BeerDTO.builder().id(UUID.randomUUID()).build();

        System.out.println(objectMapper.writeValueAsString(beer));
    }

    @Test
    @Disabled
    void testCreateNewBeer() throws Exception {
        BeerDTO beer = BeerDTO.builder().id(UUID.randomUUID()).beerName("Saku").build();

        given(saveBeerFeature.execute(any(BeerDTO.class))).willReturn(beer);

        mockMvc.perform(post(BeerController.BEER_PATH)
                .accept(String.valueOf(MediaType.APPLICATION_JSON))
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    @Disabled
    void testUpdateBeer() throws Exception {
        UUID id = UUID.randomUUID();
        BeerDTO beer = BeerDTO.builder().id(id).beerName("Saku").build();

        given(updateBeerByIdFeature.execute(any(), any())).willReturn(Optional.of(beer));

        mockMvc.perform(put(BeerController.BEER_PATH_ID, beer.getId())
                        .accept(String.valueOf(MediaType.APPLICATION_JSON))
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isNoContent());

        verify(updateBeerByIdFeature).execute(id, beer);
    }

    @Test
    @Disabled
    void testDeleteBeer() throws Exception {
        BeerDTO beer = BeerDTO.builder().id(UUID.randomUUID()).build();

        given(deleteBeerByIdFeature.execute(any())).willReturn(true);

        mockMvc.perform(delete(BeerController.BEER_PATH_ID, beer.getId())
                        .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isNoContent());

        ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);

        verify(deleteBeerByIdFeature).execute(uuidArgumentCaptor.capture());

        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());

    }

    @Test
    @Disabled
    void getBeerById() throws Exception {
        BeerDTO beer = BeerDTO.builder().id(UUID.randomUUID()).build();

        given(getBeerByIdFeature.execute(beer.getId())).willReturn(beer);

        mockMvc.perform(get(BeerController.BEER_PATH_ID, beer.getId())
                        .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(jsonPath("$.id", is(beer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(beer.getBeerName())));

        //System.out.println(beerController.getBeerById(UUID.randomUUID()));
    }

    @Test
    @Disabled
    void getBeerByIdNotFound() throws Exception {
        given(getBeerByIdFeature.execute(any(UUID.class))).willThrow(NotFoundException.class);

        mockMvc.perform(get(BeerController.BEER_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    @Disabled
    void findBeerById() throws Exception {
        BeerDTO beer = BeerDTO.builder().id(UUID.randomUUID()).build();

        given(findBeerByIdFeature.execute(beer.getId())).willReturn(Optional.of(beer));

        mockMvc.perform(get(BeerController.BEER_PATH_FIND, beer.getId())
                        .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(jsonPath("$.id", is(beer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(beer.getBeerName())));

        //System.out.println(beerController.getBeerById(UUID.randomUUID()));
    }

    @Test
    @Disabled
    void findBeerByIdNotFound() throws Exception {
        given(findBeerByIdFeature.execute(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(BeerController.BEER_PATH_FIND, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    @Disabled
    void testCreateBeerNullBeerName() throws Exception {
        BeerDTO beerDTO = BeerDTO.builder().build();

        BeerDTO savedBeer = BeerDTO.builder()
                .id(UUID.randomUUID())
                .beerName("Saku")
                .build();

        given(saveBeerFeature.execute(any())).willReturn(savedBeer);

        MvcResult mockMvcResult = mockMvc.perform(post(BeerController.BEER_PATH)
                        .accept(String.valueOf(MediaType.APPLICATION_JSON))
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(2))).andReturn();

        System.out.println(mockMvcResult.getResponse().getContentAsString());
    }

    @Test
    @Disabled
    void getAllBeer() throws Exception {
        BeerDTO beer = BeerDTO.builder().id(UUID.randomUUID()).build();

        given(getAllBeerFeature.execute(beer.getBeerName(), false, any(), any())).willReturn(null);

        mockMvc.perform(get(BeerController.BEER_PATH)
                        .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .equals(jsonPath("$.length()", is(1)));
    }
}
