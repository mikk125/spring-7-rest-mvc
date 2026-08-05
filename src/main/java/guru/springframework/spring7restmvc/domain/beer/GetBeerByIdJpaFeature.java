package guru.springframework.spring7restmvc.domain.beer;

import guru.springframework.spring6restmvcapi.model.BeerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetBeerByIdJpaFeature {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Cacheable(cacheNames = "beerCache", key = "#id")
    public BeerDTO execute(UUID id) {
        log.debug("Getting beer by id feature was called");

        Optional<Beer> result = beerRepository.findById(id);
        return result.map(beerMapper::beerToBeerDto).orElse(null);
    }
}
