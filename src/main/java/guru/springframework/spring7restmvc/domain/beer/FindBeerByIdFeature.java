package guru.springframework.spring7restmvc.domain.beer;

import guru.springframework.spring6restmvcapi.model.BeerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class FindBeerByIdFeature {

    private Map<UUID, BeerDTO> beers;

    public FindBeerByIdFeature() {
        this.beers = new HashMap<>();
    }

    public Optional<BeerDTO> execute(UUID id) {
        log.debug("Finding beer by id feature was called");

        return Optional.of(beers.get(id));
    }
}
