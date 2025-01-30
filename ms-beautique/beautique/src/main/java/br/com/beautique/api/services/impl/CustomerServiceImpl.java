package br.com.beautique.api.services.impl;

import br.com.beautique.api.dtos.CustomerDTO;
import br.com.beautique.api.entities.CustomerEntity;
import br.com.beautique.api.repositories.CustomerRepository;
import br.com.beautique.api.services.BrokerService;
import br.com.beautique.api.services.CustomerService;
import br.com.beautique.api.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private BrokerService brokerService;

    private final ConvertUtil<CustomerEntity, CustomerDTO> convertUtil = new ConvertUtil<>(CustomerEntity.class, CustomerDTO.class);

    @Override
    public CustomerDTO create(CustomerDTO customerDTO) {
        CustomerEntity customerEntity = convertUtil.convertToSource(customerDTO);
        CustomerEntity newCustomerEntity = customerRepository.save(customerEntity);
        sendCustomerTopicQueue(newCustomerEntity);
        return convertUtil.convertToTarget(newCustomerEntity);
    }

    @Override
    public void delete(Long id) {
        Optional<CustomerEntity> customerEntity = customerRepository.findById(id);
        if(customerEntity.isEmpty()){
            throw new RuntimeException("Customer not found");
        }
        customerRepository.delete(customerEntity.get());
    }

    @Override
    public CustomerDTO update(CustomerDTO customerDTO) {
        Optional<CustomerEntity> customerEntity = customerRepository.findById(customerDTO.getId());
        if(customerEntity.isEmpty()){
            throw new RuntimeException("Customer not found");
        }
        CustomerEntity customerEntityToUpdate = convertUtil.convertToSource(customerDTO);

        customerEntityToUpdate.setAppoitments(customerEntity.get().getAppoitments());
        customerEntityToUpdate.setCreatedAt(customerEntity.get().getCreatedAt());

        sendCustomerTopicQueue(customerEntityToUpdate);
        return convertUtil.convertToTarget(customerRepository.save(customerEntityToUpdate));
    }

    private void sendCustomerTopicQueue(CustomerEntity customerEntity) {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .id(customerEntity.getId())
                .name(customerEntity.getName())
                .email(customerEntity.getEmail())
                .phone(customerEntity.getPhone())
                .build();
        brokerService.send("customer", customerDTO);
    }
}
