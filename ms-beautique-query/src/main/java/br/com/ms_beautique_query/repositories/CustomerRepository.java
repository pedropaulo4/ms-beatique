package br.com.ms_beautique_query.repositories;

import br.com.ms_beautique_query.dtos.customers.CustomerDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CustomerRepository extends MongoRepository<CustomerDTO, Long> {

    @Query("{ 'name'  : { $regex: ?0, $options: 'i'}}")
    List<CustomerDTO> findByNameLikeIgnoreCase(String name);

    @Query("{ 'email'  : { $regex: ?0, $options: 'i'}}")
    List<CustomerDTO> findByEmailLikeIgnoreCase(String name);

}
