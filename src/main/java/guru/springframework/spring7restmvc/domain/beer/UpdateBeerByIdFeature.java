package guru.springframework.spring7restmvc.domain.beer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class UpdateBeerByIdFeature {

    private Map<UUID, BeerDTO> beers;

    public UpdateBeerByIdFeature() {
        this.beers = new HashMap<>();
    }

    public void execute(UUID id, BeerDTO beer) {
        BeerDTO existing = beers.get(id);

        existing.setBeerName(beer.getBeerName());
        existing.setUpc(beer.getUpc());
        existing.setPrice(beer.getPrice());
        existing.setBeerStyle(beer.getBeerStyle());
        existing.setQuantityOnHand(beer.getQuantityOnHand());

        beers.put(id, existing);
    }
}
