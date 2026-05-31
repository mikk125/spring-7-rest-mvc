package guru.springframework.spring7restmvc.domain.beer;

import guru.springframework.spring7restmvc.common.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{id}";
    public static final String BEER_PATH_FIND = BEER_PATH_ID + "/find";

    private final GetBeerByIdFeature getBeerByIdFeature;
    private final FindBeerByIdFeature findBeerByIdFeature;
    private final GetAllBeerFeature getAllBeerFeature;
    private final SaveBeerFeature saveBeerFeature;
    private final UpdateBeerByIdFeature updateBeerByIdFeature;
    private final DeleteBeerByIdFeature deleteBeerByIdFeature;

    @PostMapping(BEER_PATH)
    public ResponseEntity<BeerDTO> handlePost(@RequestBody BeerDTO beer) {
        BeerDTO savedBeer = saveBeerFeature.execute(beer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", BeerController.BEER_PATH_ID + savedBeer.getId().toString());

        return new ResponseEntity<>(savedBeer, headers, HttpStatus.CREATED);
    }

    @DeleteMapping(value = BEER_PATH_ID)
    public ResponseEntity<BeerDTO> handleDelete(@PathVariable("id") UUID id) {
        deleteBeerByIdFeature.execute(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = BEER_PATH_ID)
    public ResponseEntity<BeerDTO> handlePut(@PathVariable("id") UUID id, @RequestBody BeerDTO beer) {
        updateBeerByIdFeature.execute(id, beer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping(value = BEER_PATH)
    public List<BeerDTO> listBeers() {
        return getAllBeerFeature.execute();
    }

    @GetMapping(BEER_PATH_FIND)
    public BeerDTO findBeerById(@PathVariable("id") UUID id) {
        log.debug("Find beer by id was called in constructor");

        return findBeerByIdFeature.execute(id).orElseThrow(NotFoundException::new);
    }

    @GetMapping(value = BEER_PATH_ID)
    public BeerDTO getBeerById(@PathVariable("id") UUID id) {
        log.debug("Get beer by id was called in constructor");

        return getBeerByIdFeature.execute(id);
    }


}
