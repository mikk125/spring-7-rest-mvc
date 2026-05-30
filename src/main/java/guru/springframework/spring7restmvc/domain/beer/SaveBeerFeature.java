package guru.springframework.spring7restmvc.domain.beer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class SaveBeerFeature {

    private Map<UUID, Beer> beers;

    public SaveBeerFeature() {
        this.beers = new HashMap<>();
    }

    public Beer execute(Beer beer) {
        Beer savedBeer =  Beer.builder()
                .id(UUID.randomUUID())
                .version(beer.getVersion())
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .upc(beer.getUpc())
                .price(beer.getPrice())
                .quantityOnHand(beer.getQuantityOnHand())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        log.debug("Saving beer feature was called");

        beers.put(savedBeer.getId(), savedBeer);

        return savedBeer;
    }
}
