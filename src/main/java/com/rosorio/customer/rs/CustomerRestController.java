package com.rosorio.customer.rs;

import com.rosorio.customer.persistence.entities.CustomerInfo;
import com.rosorio.customer.persistence.entities.User;
import com.rosorio.customer.service.CustomerInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rs/customer")
public class CustomerRestController {

    private final CustomerInfoService customerInfoService;

    public CustomerRestController(CustomerInfoService customerInfoService) {
        this.customerInfoService = customerInfoService;
    }

    @GetMapping
    public ResponseEntity<CustomerInfo> getUser() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return customerInfoService.getCustomerInfo(user.getId())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
