package guru.springframework.spring7restmvc.domain.listener;


import guru.springframework.spring6restmvcapi.events.OrderPlacedEvent;
import guru.springframework.spring7restmvc.common.config.KafkaConfig;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class OrderPlacedKafkaListener {

    AtomicInteger messageCounter = new AtomicInteger(0);

    @KafkaListener(groupId = "KafkaIntegrationTest", topics = KafkaConfig.ORDER_PLACED_TOPIC)
    public void receive(OrderPlacedEvent orderPlacedEvent) {
        System.out.println("Received message: " + orderPlacedEvent);
        messageCounter.incrementAndGet();
    }
}
