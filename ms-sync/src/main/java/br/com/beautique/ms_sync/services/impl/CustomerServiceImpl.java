package br.com.beautique.ms_sync.services.impl;

import br.com.beautique.ms_sync.dtos.customers.CustomerDTO;
import br.com.beautique.ms_sync.repositories.CustomerRepository;
import br.com.beautique.ms_sync.services.CustomerService;
import br.com.beautique.ms_sync.utils.SyncLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public void saveCustomer(CustomerDTO customerDTO) {
        try {
            SyncLogger.info("saving customer: " + customerDTO.getId());
            customerRepository.save(customerDTO);
        }catch (Exception e) {
            SyncLogger.error("Error saving customer: " + e.getMessage());
            SyncLogger.trace(Arrays.toString(e.getStackTrace()));
        }
    }
}
