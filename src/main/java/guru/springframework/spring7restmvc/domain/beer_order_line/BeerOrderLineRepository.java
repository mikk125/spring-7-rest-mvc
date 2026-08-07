package guru.springframework.spring7restmvc.domain.beer_order_line;

import guru.springframework.spring7restmvc.domain.beer_order.BeerOrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerOrderLineRepository extends JpaRepository<BeerOrderLine, UUID> {
}
