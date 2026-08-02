package guru.springframework.spring7restmvc.domain.beer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteBeerByIdJpaFeature {

    private final CacheManager cacheManager;
    private final BeerRepository beerRepository;

//    @Caching(evict = {
//            @CacheEvict(cacheNames = "beerCache", key = "#beerId"),
//            @CacheEvict(cacheNames = "beerListCache") // removes data from cache
//    })
    public Boolean execute(UUID id) {
        Objects.requireNonNull(cacheManager.getCache("beerCache")).evict(id);
        Objects.requireNonNull(cacheManager.getCache("beerListCache")).clear(); //clear all

        if (beerRepository.existsById(id)) {
            beerRepository.deleteById(id);
            return true;
        }

        return false;
    }

}
