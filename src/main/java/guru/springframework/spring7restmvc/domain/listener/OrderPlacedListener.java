package guru.springframework.spring7restmvc.domain.listener;

import guru.springframework.spring6restmvcapi.events.OrderPlacedEvent;
import guru.springframework.spring7restmvc.common.config.KafkaConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderPlacedListener {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Async
    @EventListener
    public void listen(OrderPlacedEvent event) {
        log.debug("Order Placed Event received");

        kafkaTemplate.send(KafkaConfig.ORDER_PLACED_TOPIC, event);
    }
}
