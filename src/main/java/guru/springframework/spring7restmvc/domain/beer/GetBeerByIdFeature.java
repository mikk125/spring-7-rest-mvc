package guru.springframework.spring7restmvc.domain.beer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class GetBeerByIdFeature {

    private Map<UUID, BeerDTO> beers;

    public GetBeerByIdFeature() {
        this.beers = new HashMap<>();
    }

    public BeerDTO execute(UUID id) {
        log.debug("Getting beer by id feature was called");

        return beers.get(id);
    }
}
