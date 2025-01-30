package br.com.ms_beautique_query.controllers;

import br.com.ms_beautique_query.dtos.customers.CustomerDTO;
import br.com.ms_beautique_query.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    private ResponseEntity<List<CustomerDTO>> listAllCustomers() {
        return ResponseEntity.ok(customerService.listAllCustomer());
    }

    @GetMapping("/name/{name}")
    private ResponseEntity<List<CustomerDTO>> listByNameIgnoreCase(@PathVariable String name) {
        return ResponseEntity.ok(customerService.likeByNameLikeIgnoreCase(name));
    }

    @GetMapping("/email/{email}")
    private ResponseEntity<List<CustomerDTO>> listByEmailIgnoreCase(@PathVariable String email) {
        return ResponseEntity.ok(customerService.likeByEmailLikeIgnoreCase(email));
    }


}
