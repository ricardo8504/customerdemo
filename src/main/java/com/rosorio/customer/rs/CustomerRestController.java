package com.rosorio.customer.rs;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rs/customer")
public class CustomerRestController {

    @GetMapping
    public String getUser() {
        return "user info";
    }
}
