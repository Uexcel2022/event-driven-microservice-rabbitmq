package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDetailsDto;

public interface ICustomerService {
    /**
     * @param mobileNumber - customer mobile mobile
     * @return - Returns customer details with customerDetailsDto
     */
    CustomerDetailsDto getCustomerDetails(String mobileNumber,String correlationId);

    default boolean validateMobileNumber(String mobileNumber) {
        return mobileNumber.matches("^0[7-9][01][0-9]{8}$");
    }
}
