package com.rosorio.customer.service;

import com.rosorio.customer.config.cache.CachingConfig;
import com.rosorio.customer.persistence.entities.CustomerInfo;
import com.rosorio.customer.persistence.repositories.CustomerInfoRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerInfoService {

    private final CustomerInfoRepository customerInfoRepository;

    public CustomerInfoService(CustomerInfoRepository customerInfoRepository) {
        this.customerInfoRepository = customerInfoRepository;
    }

    @CachePut(value = CachingConfig.CUSTOMER_INFO_CACHE, key = "#result.id")
    public CustomerInfo saveCustomerInfo(CustomerInfo customerInfo) {
        return customerInfoRepository.save(customerInfo);
    }

    @Cacheable(value = CachingConfig.CUSTOMER_INFO_CACHE, key = "#customerId")
    public Optional<CustomerInfo> getCustomerInfo(String customerId) {
        return customerInfoRepository.findById(customerId);
    }

    @CacheEvict(value = CachingConfig.CUSTOMER_INFO_CACHE, allEntries = true)
    public void deleteAll() {
        customerInfoRepository.deleteAll();
    }
}
