package com.chalyk.taskmanager.controller;

import com.chalyk.taskmanager.dto.AccountDto;
import com.chalyk.taskmanager.facade.AccountFacade;
import com.chalyk.taskmanager.model.Account;
import com.chalyk.taskmanager.service.AccountService;
import com.chalyk.taskmanager.util.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountFacade accountFacade;
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountFacade accountFacade, AccountService accountService) {
        this.accountFacade = accountFacade;
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<AccountDto> getCurrentUser(Principal principal) {
        Account account = accountService.getCurrentAccount(principal);
        AccountDto accountDto = accountFacade.accountToAccountDto(account);

        return new ResponseEntity<>(accountDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable("id") Long id) {
        Account account = accountService.findAccountById(id);
        AccountDto accountDto = accountFacade.accountToAccountDto(account);

        return new ResponseEntity<>(accountDto, HttpStatus.OK);
    }

}
