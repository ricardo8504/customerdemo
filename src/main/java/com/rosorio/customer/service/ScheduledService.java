package com.rosorio.customer.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledService {

    private final ExternalCustomerProcessService externalCustomerProcessService;
    public ScheduledService(ExternalCustomerProcessService externalCustomerProcessService) {
        this.externalCustomerProcessService = externalCustomerProcessService;
    }

    @Scheduled(fixedRate = 1 * 60 * 1000)
    public void process() {
        externalCustomerProcessService.process();
    }

}
