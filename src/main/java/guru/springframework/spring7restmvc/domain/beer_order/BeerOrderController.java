package guru.springframework.spring7restmvc.domain.beer_order;

import guru.springframework.spring6restmvcapi.model.BeerOrderCreateDTO;
import guru.springframework.spring6restmvcapi.model.BeerOrderDTO;
import guru.springframework.spring6restmvcapi.model.BeerOrderUpdateDTO;
import guru.springframework.spring7restmvc.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

/**
 * Created by jt, Spring Framework Guru.
 */
@RestController
@RequiredArgsConstructor
public class BeerOrderController {

    public static final String BEER_ORDER_PATH = "/api/v1/beerorder";
    public static final String BEER_ORDER_PATH_ID = BEER_ORDER_PATH + "/{beerOrderId}";

    private final CreateBeerOrderJpaFeature createBeerOrderJpaFeature;
    private final GetAllBeerOrderJpaFeature getAllBeerOrderJpaFeature;
    private final GetBeerOrderByIdJpaFeature getBeerOrderByIdJpaFeature;
    private final UpdateBeerOrderByIdJpaFeature updateBeerOrderByIdJpaFeature;
    private final DeleteBeerOrderByIdJpaFeature deleteBeerOrderByIdJpaFeature;

    @PutMapping(BEER_ORDER_PATH_ID)
    public ResponseEntity<BeerOrderDTO> updateOrder(@PathVariable UUID beerOrderId, @RequestBody BeerOrderUpdateDTO beerOrderUpdateDTO) {
        return ResponseEntity.ok(updateBeerOrderByIdJpaFeature.execute(beerOrderId, beerOrderUpdateDTO));
    }

    @PostMapping(BEER_ORDER_PATH)
    public ResponseEntity<Void> createOrder(@RequestBody BeerOrderCreateDTO beerOrderCreateDTO) {
        BeerOrderDTO savedOrder =  createBeerOrderJpaFeature.execute(beerOrderCreateDTO);

        return ResponseEntity.created(URI.create(BEER_ORDER_PATH + "/" + savedOrder.getId().toString())).build();
    }

    @GetMapping(BEER_ORDER_PATH_ID)
    public BeerOrderDTO getBeerOrderById(@PathVariable UUID beerOrderId) {
        return getBeerOrderByIdJpaFeature.execute(beerOrderId).orElseThrow(NotFoundException::new);
    }

    @GetMapping(BEER_ORDER_PATH)
    public Page<BeerOrderDTO> listOrders(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return getAllBeerOrderJpaFeature.execute(pageNumber, pageSize);
    }
}
