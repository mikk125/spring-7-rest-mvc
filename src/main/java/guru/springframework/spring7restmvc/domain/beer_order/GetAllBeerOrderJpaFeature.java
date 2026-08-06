package guru.springframework.spring7restmvc.domain.beer_order;

import guru.springframework.spring6restmvcapi.model.BeerOrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetAllBeerOrderJpaFeature {

    private final BeerOrderMapper beerOrderMapper;
    private final BeerOrderRepository beerOrderRepository;

    public Page<BeerOrderDTO> execute(Integer pageNumber, Integer pageSize) {
        if (pageNumber == null || pageNumber < 0) {
            pageNumber = 0;
        }

        if (pageSize == null || pageSize < 0) {
            pageSize = 0;
        }

        return beerOrderRepository.findAll(PageRequest.of(pageNumber, pageSize)).map(beerOrderMapper::beerOrderToBeerOrderDto);
    }
}

