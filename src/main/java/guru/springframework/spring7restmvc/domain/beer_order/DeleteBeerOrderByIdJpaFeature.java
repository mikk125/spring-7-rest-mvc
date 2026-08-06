package guru.springframework.spring7restmvc.domain.beer_order;

import guru.springframework.spring7restmvc.common.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteBeerOrderByIdJpaFeature {

    private final BeerOrderRepository beerOrderRepository;

    public void execute(UUID beerOrderId) {
        if (beerOrderRepository.existsById(beerOrderId)) {
            beerOrderRepository.deleteById(beerOrderId);
        } else {
            throw new NotFoundException();
        }
    }
}
