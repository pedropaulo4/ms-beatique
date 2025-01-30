package br.com.ms_beautique_query.services;

import br.com.ms_beautique_query.dtos.customers.CustomerDTO;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> listAllCustomer();
    List<CustomerDTO> likeByNameLikeIgnoreCase(String name);
    List<CustomerDTO> likeByEmailLikeIgnoreCase(String email);
}
