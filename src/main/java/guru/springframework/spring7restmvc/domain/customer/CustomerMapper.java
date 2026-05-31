package guru.springframework.spring7restmvc.domain.customer;

import guru.springframework.spring7restmvc.domain.customer.Customer;
import guru.springframework.spring7restmvc.domain.customer.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO dto);
    CustomerDTO customerToCustomerDto(Customer customer);
}

