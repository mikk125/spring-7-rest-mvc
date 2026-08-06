package guru.springframework.spring7restmvc.domain.beer_order;

import guru.springframework.spring6restmvcapi.events.OrderPlacedEvent;
import guru.springframework.spring6restmvcapi.model.BeerOrderDTO;
import guru.springframework.spring6restmvcapi.model.BeerOrderUpdateDTO;
import guru.springframework.spring7restmvc.common.exception.NotFoundException;
import guru.springframework.spring7restmvc.domain.beer.BeerRepository;
import guru.springframework.spring7restmvc.domain.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateBeerOrderByIdJpaFeature {

    private final BeerOrderRepository beerOrderRepository;
    private final CustomerRepository customerRepository;
    private final BeerRepository beerRepository;
    private final BeerOrderMapper beerOrderMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    public BeerOrderDTO execute(UUID beerOrderid, BeerOrderUpdateDTO beerOrderUpdateDTO) {
        val order = beerOrderRepository.findById(beerOrderid).orElseThrow(NotFoundException::new);

        order.setCustomer(customerRepository.findById(beerOrderUpdateDTO.getCustomerId()).orElseThrow(NotFoundException::new));
        order.setCustomerRef(beerOrderUpdateDTO.getCustomerRef());

        beerOrderUpdateDTO.getBeerOrderLines().forEach(beerOrderLine -> {
            if (beerOrderLine.getBeerId() != null) {
                val foundLine = order.getBeerOrderLines().stream()
                        .filter(beerOrderLine1 -> beerOrderLine1.getId().equals(beerOrderLine.getId()))
                        .findFirst().orElseThrow(NotFoundException::new);

                foundLine.setBeer(beerRepository.findById(beerOrderLine.getBeerId()).orElseThrow(NotFoundException::new));
                foundLine.setOrderQuantity(beerOrderLine.getOrderQuantity());
                foundLine.setQuantityAllocated(beerOrderLine.getQuantityAllocated());
            } else {
                order.getBeerOrderLines().add(BeerOrderLine.builder()
                        .beer(beerRepository.findById(beerOrderLine.getBeerId()).orElseThrow(NotFoundException::new))
                        .orderQuantity(beerOrderLine.getOrderQuantity())
                        .quantityAllocated(beerOrderLine.getQuantityAllocated())
                        .build());
            }
        });

        if (beerOrderUpdateDTO.getBeerOrderShipment() != null && beerOrderUpdateDTO.getBeerOrderShipment().getTrackingNumber() != null) {
            if (order.getBeerOrderShipment() == null) {
                order.setBeerOrderShipment(BeerOrderShipment.builder().trackingNumber(beerOrderUpdateDTO.getBeerOrderShipment().getTrackingNumber()).build());
            } else {
                order.getBeerOrderShipment().setTrackingNumber(beerOrderUpdateDTO.getBeerOrderShipment().getTrackingNumber());
            }
        }

        BeerOrderDTO dto = beerOrderMapper.beerOrderToBeerOrderDto(beerOrderRepository.save(order));

        if (beerOrderUpdateDTO.getPaymentAmount() != null) {
            applicationEventPublisher.publishEvent(OrderPlacedEvent.builder()
                    .beerOrderDTO(dto)
                    .build());
        }

        return dto;
    }
}
