package com.rosorio.customer.persistence.repositories;

import com.rosorio.customer.persistence.entities.CustomerInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerInfoRepository extends MongoRepository<CustomerInfo, String> {
}
