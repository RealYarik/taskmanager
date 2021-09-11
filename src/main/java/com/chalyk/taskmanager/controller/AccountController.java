package com.chalyk.taskmanager.controller;

import com.chalyk.taskmanager.dto.AccountDto;
import com.chalyk.taskmanager.facade.AccountFacade;
import com.chalyk.taskmanager.model.Account;
import com.chalyk.taskmanager.payload.response.MessageResponse;
import com.chalyk.taskmanager.service.AccountService;
import com.chalyk.taskmanager.util.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountFacade accountFacade;
    private final AccountService accountService;
    private final ResponseErrorValidation errorValidation;


    @Autowired
    public AccountController(AccountFacade accountFacade, AccountService accountService, ResponseErrorValidation errorValidation) {
        this.accountFacade = accountFacade;
        this.accountService = accountService;
        this.errorValidation = errorValidation;
    }

    @GetMapping("/current")
    public ResponseEntity<AccountDto> getCurrentUser(Principal principal) {
        Account account = accountService.getCurrentAccount(principal);
        AccountDto accountDto = accountFacade.accountToAccountDto(account);

        return new ResponseEntity<>(accountDto, HttpStatus.OK);
    }

    @GetMapping("/{login}")
    public ResponseEntity<AccountDto> getAccountByLogin(@PathVariable("login") String login) {
        Account account = accountService.findAccountByLogin(login);
        AccountDto accountDto = accountFacade.accountToAccountDto(account);

        return new ResponseEntity<>(accountDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        List<Account> accounts = accountService.findAll();
        List<AccountDto> accountsDto = accounts.stream()
                .map(accountFacade::accountToAccountDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(accountsDto, HttpStatus.OK);
    }

    @GetMapping("/logins")
    public ResponseEntity<List<String>> getAccountLogins(Principal principal) {
        List<String> accountLogins = accountService.findAccountLoginsWithoutPrincipal(principal.getName());

        return new ResponseEntity<>(accountLogins, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PatchMapping
    public ResponseEntity<Object> updateCurrentAccount(Principal principal, @Valid @RequestBody AccountDto accountDto, BindingResult bindingResult) {
        ResponseEntity<Object> errors = errorValidation.mapValidationService(bindingResult);

        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }
        Account account = accountService.getAccountByPrincipal(principal);
        account.setFirstName(accountDto.getFirstName());
        account.setLastName(accountDto.getLastName());
        accountService.updateAccount(account);

        return ResponseEntity.ok(new MessageResponse("Account updated successfully"));
    }

}
