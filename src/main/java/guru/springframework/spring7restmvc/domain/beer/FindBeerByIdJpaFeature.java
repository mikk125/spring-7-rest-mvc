package guru.springframework.spring7restmvc.domain.beer;

import guru.springframework.spring6restmvcapi.model.BeerDTO;
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
public class FindBeerByIdJpaFeature {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    public Optional<BeerDTO> execute(UUID id) {
        log.debug("Finding beer by id feature was called");

        return beerRepository.findById(id).map(beerMapper::beerToBeerDto);
    }
}
