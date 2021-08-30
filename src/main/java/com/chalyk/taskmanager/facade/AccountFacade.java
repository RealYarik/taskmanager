package com.chalyk.taskmanager.facade;

import com.chalyk.taskmanager.dto.AccountDto;
import com.chalyk.taskmanager.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountFacade {

    public AccountDto accountToAccountDto(Account account) {
        AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setFirstName(account.getFirstName());
        accountDto.setLastName(account.getLastName());
        accountDto.setLogin(account.getLogin());
        accountDto.setGender(account.getGender());

        return accountDto;
    }
}
