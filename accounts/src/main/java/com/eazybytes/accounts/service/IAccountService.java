package com.eazybytes.accounts.service;

import com.eazybytes.accounts.constants.AccountConstants;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Account;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.InvalidArgException;

import java.util.Random;

public interface IAccountService {
    /**
     * @param   customerDto - Customer Object
      * */
    void createAccount(CustomerDto customerDto);

    /**
     * @param mobileNumber -mobileNumber
     * @return customer details
     */
    CustomerDto getCustomerByMobileNumber(String mobileNumber);

    /**
     * @param customerDto - mobileNumber
     * @return a boolean value indicating account is updated or not
     */
    boolean updateCustomer(CustomerDto customerDto);

    /**
     * @param mobileNumber - mobileNumber
     * @return return boolean value indicating account is deleted or not
     */
    boolean deleteCustomer(String mobileNumber);


    /**
     * @param accountNumber  - accountNumber
     * @return - boolean
     */
    boolean updateComminucationStatus(Long accountNumber);


    /**
     * @param customer - Custer Object
     * @return new account details
     */
    default Account getNewAccount(Customer customer) {
        Long randomAccNo = 1000000000L + new Random().nextInt(900000000);
        Account newAccount = new Account();
        newAccount.setCustomerId(customer.getCustomerId());
        newAccount.setAccountNumber(randomAccNo);
        newAccount.setAccountType(AccountConstants.SAVINGS);
        newAccount.setBranchAddress(AccountConstants.ADDRESS);
        return newAccount;
    }


    default void validateMobileNumber(String mobileNumber) {
        boolean isValid = mobileNumber.matches("^0[7-9][01][0-9]{8}$");
        if(!isValid){
            throw new InvalidArgException(
                    String.format("Mobile number %s is not invalid", mobileNumber));
        }
    }
}
