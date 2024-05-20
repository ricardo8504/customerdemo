package com.rosorio.customer.rs;

import com.rosorio.customer.persistence.entities.CustomerInfo;
import com.rosorio.customer.persistence.entities.User;
import com.rosorio.customer.service.CustomerInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rs/admin/customer")
public class CustomerRestAdminController {

    private final CustomerInfoService customerInfoService;

    public CustomerRestAdminController(CustomerInfoService customerInfoService) {
        this.customerInfoService = customerInfoService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerInfo> getUser(@PathVariable("customerId") String customerId) {
        return customerInfoService.getCustomerInfo(customerId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
