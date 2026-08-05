package guru.springframework.spring7restmvc.domain.beer;

import guru.springframework.spring6restmvcapi.model.BeerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class DeleteBeerByIdFeature {

    private Map<UUID, BeerDTO> beers;

    public DeleteBeerByIdFeature() {
        this.beers = new HashMap<>();
    }

    public void execute(UUID id) {
        beers.remove(id);
    }

}
