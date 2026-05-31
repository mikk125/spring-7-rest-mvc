package guru.springframework.spring7restmvc.domain.customer;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testSaveCustomer() {
        Customer savedCustomer = customerRepository.save(Customer.builder().name("Martin Roberts").build());

        customerRepository.flush(); // tells jpa to immediately save the customer

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isNotNull();
    }

    @Test
    void testSaveCustomerNameTooLong() {
        assertThrows(ConstraintViolationException.class, () -> {
            Customer savedCustomer = customerRepository.save(Customer.builder().name("n".repeat(51)).build());

            customerRepository.flush(); // tells jpa to immediately save the customer
        });
    }
}

