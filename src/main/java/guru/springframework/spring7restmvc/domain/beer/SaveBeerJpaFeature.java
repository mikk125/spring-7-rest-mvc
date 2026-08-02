package guru.springframework.spring7restmvc.domain.beer;

import guru.springframework.spring7restmvc.domain.event.BeerCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaveBeerJpaFeature {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final CacheManager cacheManager;

    public BeerDTO execute(BeerDTO beer) {
        if (cacheManager.getCache("beerListCache") != null) {
            cacheManager.getCache("beerListCache").clear();
        }
        val savedBeer = beerRepository.save(beerMapper.beerDtoToBeer(beer));

        System.out.println("Current Thread name: " + Thread.currentThread().getName());
        System.out.println("Current Thread ID: " + Thread.currentThread().getId());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        applicationEventPublisher.publishEvent(new BeerCreatedEvent(savedBeer, auth));

        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beer)));
    }
}
