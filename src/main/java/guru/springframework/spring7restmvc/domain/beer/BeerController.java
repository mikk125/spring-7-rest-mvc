package guru.springframework.spring7restmvc.domain.beer;

import guru.springframework.spring7restmvc.domain.customer.Customer;
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
@RequestMapping("/api/v1/beer")
public class BeerController {

    private final GetBeerByIdFeature getBeerByIdFeature;
    private final GetAllBeerFeature getAllBeerFeature;
    private final SaveBeerFeature saveBeerFeature;
    private final UpdateBeerByIdFeature updateBeerByIdFeature;
    private final DeleteBeerByIdFeature deleteBeerByIdFeature;

    @PostMapping
    public ResponseEntity<Beer> handlePost(@RequestBody  Beer beer) {
        Beer savedBeer = saveBeerFeature.execute(beer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + savedBeer.getId().toString());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Beer> handleDelete(@PathVariable("id") UUID id) {
        deleteBeerByIdFeature.execute(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Beer> handlePut(@PathVariable("id") UUID id, @RequestBody Beer beer) {
        updateBeerByIdFeature.execute(id, beer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> listBeers() {
        return getAllBeerFeature.execute();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Beer getBeerById(@PathVariable("id") UUID id) {
        log.debug("Get beer by id was called in constructor");

        return getBeerByIdFeature.execute(id);
    }


}
