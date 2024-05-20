package com.rosorio.customer.service.mapper;

import com.rosorio.customer.dto.ExternalCustomerInfoDto;
import com.rosorio.customer.persistence.entities.CustomerInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerInfoMapper {

    @Mapping(target = "creditCardNum", expression = "java(TextUtil.mask(externalCustomerInfoDto.getCreditCardNum()))")
    @Mapping(target = "creditCardCcv", expression = "java(TextUtil.mask(externalCustomerInfoDto.getCreditCardCcv(), 0))")
    @Mapping(target = "cuentaNumero", expression = "java(TextUtil.mask(externalCustomerInfoDto.getCuentaNumero()))")
    CustomerInfo externalCustomerToLocalCustomer(ExternalCustomerInfoDto externalCustomerInfoDto);
}