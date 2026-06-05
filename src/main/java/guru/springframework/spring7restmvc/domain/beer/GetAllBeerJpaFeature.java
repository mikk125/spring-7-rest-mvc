package guru.springframework.spring7restmvc.domain.beer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetAllBeerJpaFeature {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    public List<BeerDTO> execute(String beerName) {
        List<Beer> beerList;

        if (StringUtils.hasText(beerName)) {
            beerList = listBeersByName(beerName);
        } else {
            beerList = beerRepository.findAll();
        }

        return beerList
                .stream()
                .map(beerMapper::beerToBeerDto)
                .toList();
    }

    public List<Beer> listBeersByName(String beerName) {
        return beerRepository.findAllByBeerNameIsLikeIgnoreCase(String.format("%%%s%%", beerName));
    }
}
