package com.eazybytes.accounts.mapper;

import com.eazybytes.accounts.constants.AccountConstants;
import com.eazybytes.accounts.dto.AccountDto;
import com.eazybytes.accounts.entity.Account;
import com.eazybytes.accounts.entity.Customer;

import java.time.LocalDateTime;
import java.util.Random;

public class AccountMapper {
    public static Account mapToAccount(AccountDto accountDto,Account account) {
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setAccountType(accountDto.getAccountType());
        account.setBranchAddress(account.getBranchAddress());
        return account;
    }

    public static AccountDto mapToAccountDto(Account account) {
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setAccountType(account.getAccountType());
        accountDto.setBranchAddress(account.getBranchAddress());
        return accountDto;
    }


}
