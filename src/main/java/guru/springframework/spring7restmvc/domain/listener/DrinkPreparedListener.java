package guru.springframework.spring7restmvc.domain.listener;

import guru.springframework.spring6restmvcapi.events.DrinkPreparedEvent;
import guru.springframework.spring6restmvcapi.model.BeerOrderLineStatus;
import guru.springframework.spring7restmvc.common.config.KafkaConfig;
import guru.springframework.spring7restmvc.domain.beer_order_line.BeerOrderLineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DrinkPreparedListener {

    private final BeerOrderLineRepository beerOrderLineRepository;

    @KafkaListener(topics = KafkaConfig.DRINK_PREPARED_TOPIC, groupId = "DrinkPreparedListener")
    public void listen(DrinkPreparedEvent event) {
        event.getBeerOrderDTO().getBeerOrderLines().forEach(line -> {
            beerOrderLineRepository.findById(line.getId()).ifPresentOrElse(line2 -> {
                log.debug(String.format("Beer %s is completed.", line2.getBeer().getBeerName()));

                line2.setOrderLineStatus(BeerOrderLineStatus.COMPLETE);

                beerOrderLineRepository.save(line2);
            }, () -> log.error("Beer Order line Not found"));
        });
    }
}
