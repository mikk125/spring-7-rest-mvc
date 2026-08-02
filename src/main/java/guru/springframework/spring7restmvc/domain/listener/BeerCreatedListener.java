package guru.springframework.spring7restmvc.domain.listener;

import guru.springframework.spring7restmvc.domain.beer.BeerAuditRepository;
import guru.springframework.spring7restmvc.domain.beer.BeerMapper;
import guru.springframework.spring7restmvc.domain.event.BeerCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerCreatedListener {

    private final BeerMapper beerMapper;
    private final BeerAuditRepository beerAuditRepository;

    @Async // thanks to TaskConf thread ids are different
    @EventListener
    public void listen(BeerCreatedEvent event) {
//        System.out.println("I heard a beer was created");
//        System.out.println(event.getBeer().getId());
//
//        System.out.println("Current Thread name: " + Thread.currentThread().getName());
//        System.out.println("Current Thread ID: " + Thread.currentThread().getId());
//
//        // insert audit record

        var beerAudit = beerMapper.beerToBeerAudit(event.getBeer());
        beerAudit.setAuditEventType("BEER_CREATED");

        if (event.getAuthentication() != null && event.getAuthentication().getName() != null) {
            beerAudit.setPrincipalName(event.getAuthentication().getName());
        }

        val savedBeerAudit = beerAuditRepository.save(beerAudit);
        log.debug("Beer audit saved: " + savedBeerAudit.getId());


    }
}
