package guru.springframework.spring7restmvc.domain.customer;

import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriTemplate;
import tools.jackson.databind.ObjectMapper;

import javax.print.attribute.standard.Media;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


//@SpringBootTest
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

//    @Autowired
//    CustomerController customerController;

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    GetCustomerByIdFeature getCustomerByIdFeature;

    @MockitoBean
    GetAllCustomerFeature getAllCustomerFeature;

    @MockitoBean
    SaveCustomerFeature saveCustomerFeature;

    @MockitoBean
    UpdateCustomerByIdFeature updateCustomerByIdFeature;

    @MockitoBean
    DeleteCustomerByIdFeature deleteCustomerByIdFeature;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testCreateCustomer() {
        Customer customer = Customer.builder().id(UUID.randomUUID()).build();

        System.out.println(objectMapper.writeValueAsString(customer));
    }

    @Test
    void testCreateNewCustomer() throws Exception {
        Customer customer = Customer.builder().id(UUID.randomUUID()).name("Robert Alice").build();

        given(saveCustomerFeature.execute(any(Customer.class))).willReturn(customer);

        mockMvc.perform(post(CustomerController.CUSTOMER_PATH)
                        .accept(String.valueOf(MediaType.APPLICATION_JSON))
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testUpdateCustomer() throws Exception {
        UUID id = UUID.randomUUID();
        Customer customer = Customer.builder().id(id).name("Robert Alice").build();

        mockMvc.perform(put(CustomerController.CUSTOMER_PATH_ID, customer.getId())
                        .accept(String.valueOf(MediaType.APPLICATION_JSON))
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isNoContent());

        verify(updateCustomerByIdFeature).execute(id, customer);
    }

    @Test
    void testDeleteCustomer() throws Exception {
        Customer customer = Customer.builder().id(UUID.randomUUID()).build();

        mockMvc.perform(delete(CustomerController.CUSTOMER_PATH_ID, customer.getId())
                        .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isNoContent());

        ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);

        verify(deleteCustomerByIdFeature).execute(uuidArgumentCaptor.capture());

        assertThat(customer.getId()).isEqualTo(uuidArgumentCaptor.getValue());

    }

    @Test
    void getCustomerById() throws Exception {
        Customer customer = Customer.builder().id(UUID.randomUUID()).build();

        given(getCustomerByIdFeature.execute(customer.getId())).willReturn(customer);

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, customer.getId())
                        .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(jsonPath("$.id", is(customer.getId().toString())))
                .andExpect(jsonPath("$.name", is(customer.getName())));

        //System.out.println(customerController.getCustomerById(UUID.randomUUID()));
    }

    @Test
    void getAllCustomer() throws Exception {
        Customer customer = Customer.builder().id(UUID.randomUUID()).build();

        given(getAllCustomerFeature.execute()).willReturn(List.of(customer));

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH)
                        .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .equals(jsonPath("$.length()", is(1)));
    }
}
