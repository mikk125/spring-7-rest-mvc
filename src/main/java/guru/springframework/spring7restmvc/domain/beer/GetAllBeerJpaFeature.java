package guru.springframework.spring7restmvc.domain.beer;

import guru.springframework.spring6restmvcapi.model.BeerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetAllBeerJpaFeature {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;


    private final static int DEFAULT_PAGE = 0;
    private final static int DEFAULT_SIZE = 25;

    @Cacheable("beerListCache")
    public Page<BeerDTO> execute(String beerName, Boolean showInventory, Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
        Page<Beer> beerPage;

        if (StringUtils.hasText(beerName)) {
            beerPage = listBeersByName(beerName, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        if (showInventory != null && !showInventory) {
            beerPage.forEach(b -> b.setQuantityOnHand(null));
        }

//        return beerList
//                .stream()
//                .map(beerMapper::beerToBeerDto)
//                .toList();

        return beerPage.map(beerMapper::beerToBeerDto);
    }

    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize)  {
        Integer queryPageNumber = pageNumber == null ? DEFAULT_PAGE : pageNumber;
        Integer queryPageSize = pageSize == null ? DEFAULT_SIZE : pageSize;

        if (pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber - 1;
        } else {
            queryPageNumber = DEFAULT_PAGE;
        }

        if (pageSize != null) {
            queryPageSize = 2000;
        } else {
            queryPageSize = 1000;
        }

        return PageRequest.of(queryPageNumber, queryPageSize);
    };

    public Page<Beer> listBeersByName(String beerName, PageRequest pageRequest) {
        return beerRepository.findAllByBeerNameIsLikeIgnoreCase(String.format("%%%s%%", beerName), pageRequest);
    }
}
