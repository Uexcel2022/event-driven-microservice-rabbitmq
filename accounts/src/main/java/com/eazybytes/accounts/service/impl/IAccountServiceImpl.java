package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.dto.AccountMsgDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Account;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.InvalidArgException;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class IAccountServiceImpl implements IAccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final StreamBridge streamBridge;

    private final Logger logger = LoggerFactory.getLogger(IAccountServiceImpl.class);

    /**
     * @param customerDto - Customer Object
     */
    @Override
    @Transactional
    public void createAccount(CustomerDto customerDto) {
        if(customerRepository
                .existsByMobileNumber(customerDto.getMobileNumber())) {
            throw new InvalidArgException(
                    String.format("Mobile number %s has been used.", customerDto.getMobileNumber())
            );
        }
        Customer customer =  CustomerMapper.mapToCustomer(customerDto,new Customer());
        Customer createdCustomer = customerRepository.save( customer);
        Account account = accountRepository.save(getNewAccount(createdCustomer));
        sendCommunication(account,createdCustomer);
    }

    /**
     * @param mobileNumber
     * @return customer details
     */
    @Override
    @Transactional
    public CustomerDto getCustomerByMobileNumber(String mobileNumber) {
        Customer customer =
                customerRepository.findByMobileNumber(mobileNumber)
                        .orElseThrow(()-> new
                                ResourceNotFoundException("Customer","mobileNumber", mobileNumber));

        Account account =
                accountRepository.findByCustomerId(customer.getCustomerId())
                        .orElseThrow(()-> new
                                ResourceNotFoundException("Customer","customerId",
                                customer.getCustomerId().toString()));

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer,new CustomerDto());
        customerDto.setAccountDto(AccountMapper.mapToAccountDto(account));

        return customerDto;
    }

    /**
     * @param customerDto
     * @return a boolean value indicating account is updated or not
     */
    @Override
    @Transactional
    public boolean updateCustomer(CustomerDto customerDto) {
        Long accountNo =
                customerDto.getAccountDto().getAccountNumber();

        if(accountNo != null) {
            Account account =
                    accountRepository
                            .findByAccountNumber(accountNo)
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Account", "accountNumber", accountNo.toString()));
            accountRepository.save(AccountMapper
                    .mapToAccount(customerDto.getAccountDto(), account));

            Customer customer =
                    customerRepository.findById(account.getCustomerId())
                            .orElseThrow(() -> new
                                    ResourceNotFoundException(
                                    "Customer", "customerId",
                                    account.getCustomerId().toString()));

            customerRepository.save(CustomerMapper.mapToCustomer(customerDto, customer));

            return true;
        }

        throw new ResourceNotFoundException("Account", "accountNumber", null);
    }

    /**
     * @param mobileNumber - mobileNumber
     * @return return boolean value indicating account is deleted or not
     */
    @Override
    @Transactional
    public boolean deleteCustomer(String mobileNumber) {
        if(mobileNumber != null){
            Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                    .orElseThrow(()-> new ResourceNotFoundException(
                            "Customer", "mobileNumber", mobileNumber));
            Account account = accountRepository.findByCustomerId(customer.getCustomerId())
                    .orElseThrow(()-> new ResourceNotFoundException(
                            "Account","customerId",customer.getCustomerId().toString()));
            accountRepository.delete(account);
            customerRepository.delete(customer);
            return true;
        }

        return false;
    }

    private void  sendCommunication(Account account, Customer customer){
        AccountMsgDto accountMsgDto = new
                AccountMsgDto(account.getAccountNumber(),
                customer.getName(), customer.getEmail(), customer.getMobileNumber());
        logger.info("Sending communication request for the details:  {}",accountMsgDto);
        var result =  streamBridge.send("sendCommunication-out-0", accountMsgDto);
        logger.info("Is the communication request triggered successfully?: {}", result);
    }


    /**
     * @param accountNumber - accountNumber
     * @return boolean
     */
    @Override
    public boolean updateComminucationStatus(Long accountNumber) {
        boolean isUpdated = false;
        if(accountNumber != null) {
            Account account = accountRepository.findByAccountNumber(accountNumber)
                    .orElseThrow(() -> new
                            ResourceNotFoundException("Account", "accountNumber", Long.toString(accountNumber)));
            account.setCommunicationSw(true);
            accountRepository.save(account);
            isUpdated = true;
        }
        return  isUpdated;
    }

}
