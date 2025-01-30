package br.com.beautique.ms_sync.repositories;

import br.com.beautique.ms_sync.dtos.customers.CustomerDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<CustomerDTO, Long> {
}
