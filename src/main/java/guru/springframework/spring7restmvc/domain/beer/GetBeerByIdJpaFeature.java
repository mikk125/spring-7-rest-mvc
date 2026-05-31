package guru.springframework.spring7restmvc.domain.beer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public BeerDTO execute(UUID id) {
        log.debug("Getting beer by id feature was called");

        Optional<Beer> result = beerRepository.findById(id);
        return result.map(beerMapper::beerToBeerDto).orElse(null);
    }
}
