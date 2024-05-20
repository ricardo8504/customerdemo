package com.rosorio.customer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rosorio.customer.dto.ExternalCustomerInfoDto;
import com.rosorio.customer.persistence.entities.CustomerInfo;
import com.rosorio.customer.service.mapper.CustomerInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ExternalCustomerProcessService {

    @Value("${external.customer.uri}")
    private String externalApiUri;
    private final ObjectMapper objectMapper;
    private final CustomerInfoMapper customerInfoMapper;
    private final CustomerInfoService customerInfoService;

    public ExternalCustomerProcessService(ObjectMapper objectMapper, CustomerInfoMapper customerInfoMapper,
                                          CustomerInfoService customerInfoService) {
        this.objectMapper = objectMapper;
        this.customerInfoMapper = customerInfoMapper;
        this.customerInfoService = customerInfoService;
    }

    public void process() {
        try {
            log.info("Processing external customer info ");
            String jsonResponse = new RestTemplate().getForObject(externalApiUri, String.class);
            var customerInfoList = objectMapper.readValue(jsonResponse,
                    new TypeReference<List<ExternalCustomerInfoDto>>(){});
            customerInfoService.deleteAll();
            Optional.ofNullable(customerInfoList)
                    .ifPresent(customers -> customers.forEach(customer -> {
                        CustomerInfo customerInfo = customerInfoMapper.externalCustomerToLocalCustomer(customer);
                        customerInfoService.saveCustomerInfo(customerInfo);
                    }));
            log.info("Processed {} customers from external source", customerInfoList != null? customerInfoList.size() : "0");
        } catch (RestClientException restClientException) {
            log.error("Error while processing external customer info call", restClientException);
        } catch (JsonMappingException jsonException) {
            log.error("Error while mapping json response", jsonException);
        } catch (JsonProcessingException jsonProcessingException) {
            log.error("Error while processing json response", jsonProcessingException);
        }
    }
}
