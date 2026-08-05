package guru.springframework.spring7restmvc.domain.beer;

import guru.springframework.spring6restmvcapi.model.BeerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateBeerByIdJpaFeature {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    public Optional<BeerDTO> execute(UUID id, BeerDTO beer) {
        return Optional.of(beerMapper.beerToBeerDto(
                beerRepository.save(beerMapper.beerDtoToBeer(beer))
        ));

//        AtomicReference<Optional<BeerDTO>> result = new AtomicReference<>();
//
//        beerRepository.findById(id).ifPresentOrElse(fB -> {
//            fB.setBeerName(beer.getBeerName());
//            fB.setBeerStyle(beer.getBeerStyle());
//            fB.setUpc(beer.getUpc());
//            fB.setPrice(beer.getPrice());
//            fB.setQuantityOnHand(beer.getQuantityOnHand());
//            fB.setVersion(beer.getVersion());
//            result.set(Optional.of(beerMapper.beerToBeerDto(beerRepository.save(fB))));
//        }, () -> result.set(Optional.empty()));
//
//        return result.get();
    }
}
