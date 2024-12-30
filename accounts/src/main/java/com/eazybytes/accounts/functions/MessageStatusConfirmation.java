package com.eazybytes.accounts.functions;

import com.eazybytes.accounts.service.IAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class MessageStatusConfirmation {
    private final Logger logger = LoggerFactory.getLogger(MessageStatusConfirmation.class);
    @Bean
    public Consumer<Long> updateCommunication(IAccountService iAccountService){
        return accountNumber -> {
            logger.info("Updating communication status for accountNumber {}: " ,accountNumber);
            boolean isOk =  iAccountService.updateComminucationStatus(accountNumber);
            logger.info("Is massage communication status updated successfully? {}: " ,isOk);
        };
    }
}
