package guru.springframework.spring7restmvc.domain.customer;

import guru.springframework.spring6restmvcapi.model.CustomerDTO;
import guru.springframework.spring7restmvc.common.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CustomerControllerIT {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void deleteByIdNotFoundTest() {
        assertThrows(NotFoundException.class, () -> customerController.handleDelete(UUID.randomUUID()));
    }

    @Rollback
    @Transactional
    @Test
    void deleteByIdTest() {
        Customer customer = customerRepository.findAll().get(0);

        ResponseEntity responseEntity = customerController.handleDelete(customer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(customerRepository.findById(customer.getId())).isEmpty();
    }

    @Test
    void updateNotFoundTest() {
        assertThrows(NotFoundException.class, () -> customerController.handlePut(UUID.randomUUID(), CustomerDTO.builder().build()));
    }

    @Rollback
    @Transactional
    @Test
    void saveNewCustomerTest() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("Martin Evans")
                .build();

        ResponseEntity responseEntity = customerController.handlePost(customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Customer customer = customerRepository.findById(savedUUID).get();
        assertThat(customer).isNotNull();
    }


    @Test
    void testCustomerIdNotFound() {
        assertNull(customerController.getCustomerById(UUID.randomUUID()));

        customerController.getCustomerById(UUID.randomUUID());
    }

    @Test
    void testGetById() {
        Customer customer = customerRepository.findAll().get(0);

        CustomerDTO dto = customerController.getCustomerById(customer.getId());
        assertThat(dto).isNotNull();
    }

    @Test
    void testListCustomers() {
        List<CustomerDTO> dtos = customerController.listCustomers();

        assertThat(dtos.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        customerRepository.deleteAll();

        List<CustomerDTO> dtos = customerController.listCustomers();

        assertThat(dtos.size()).isEqualTo(0);
    }
}
