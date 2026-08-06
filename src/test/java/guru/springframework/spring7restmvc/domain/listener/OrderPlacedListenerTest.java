package guru.springframework.spring7restmvc.domain.listener;

import guru.springframework.spring6restmvcapi.events.OrderPlacedEvent;
import guru.springframework.spring6restmvcapi.model.BeerOrderDTO;
import guru.springframework.spring7restmvc.common.config.KafkaConfig;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistrar;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestConstructor;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EmbeddedKafka(controlledShutdown = true, topics = {KafkaConfig.ORDER_PLACED_TOPIC}, partitions = 1)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class OrderPlacedListenerTest {

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @Autowired
    private OrderPlacedKafkaListener orderPlacedKafkaListener;

    @Autowired
    private OrderPlacedListener orderPlacedListener;

    @BeforeEach
    void setUp() {
        kafkaListenerEndpointRegistry.getListenerContainers().forEach(container -> {
            ContainerTestUtils.waitForAssignment(container, 1);
        });
    }

    @Test
    void listen() {
        OrderPlacedEvent orderPlacedEvent = OrderPlacedEvent.builder().beerOrderDTO(BeerOrderDTO.builder()
                .id(UUID.randomUUID())
                .build()).build();

        orderPlacedListener.listen(orderPlacedEvent);

        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            assertEquals(1, orderPlacedKafkaListener.messageCounter.get());
        });
    }
}