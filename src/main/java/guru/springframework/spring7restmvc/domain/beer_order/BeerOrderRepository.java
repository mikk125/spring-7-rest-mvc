package guru.springframework.spring7restmvc.domain.beer_order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BeerOrderRepository extends JpaRepository<BeerOrder, UUID> {
}
