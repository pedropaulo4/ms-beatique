package br.com.beautique.api.services;

import br.com.beautique.api.dtos.CustomerDTO;

public interface CustomerService {

    CustomerDTO create(CustomerDTO customerDTO);
    void delete(Long id);
    CustomerDTO update(CustomerDTO customerDTO);
}
