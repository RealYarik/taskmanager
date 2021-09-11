package com.chalyk.taskmanager.service;

import com.chalyk.taskmanager.exception.UserExistException;
import com.chalyk.taskmanager.model.Account;
import com.chalyk.taskmanager.model.Gender;
import com.chalyk.taskmanager.model.Role;
import com.chalyk.taskmanager.payload.request.RegistrationRequest;
import com.chalyk.taskmanager.repo.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return accountRepository.findAccountByLogin(s).orElse(new Account());
    }

    public void updateAccount(Account account) {
        accountRepository.save(account);
    }

    public void addAccount(RegistrationRequest registrationRequest) {
        Account account = new Account();
        account.setGender(Gender.MALE);
        account.setCreateDate(LocalDateTime.now());
        account.setActive(true);

        Set<Role> roles = Arrays.stream(Role.values())
                .filter(role -> role.equals(Role.USER))
                .collect(Collectors.toSet());

        account.setRoles(roles);
        account.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        account.setFirstName(registrationRequest.getFirstName());
        account.setLastName(registrationRequest.getLastName());
        account.setLogin(registrationRequest.getLogin());
        try {
            accountRepository.save(account);
        } catch (Exception e) {
            throw new UserExistException("This user " + account.getLogin() + " already exist");
        }
    }

    public Account getCurrentAccount(Principal principal) {
        return getAccountByPrincipal(principal);
    }

    public Account getAccountByPrincipal(Principal principal) {
        String login = principal.getName();
        return accountRepository.findAccountByLogin(login).orElse(new Account());
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account findAccountByLogin(String login) {
        return accountRepository.findAccountByLogin(login).orElse(new Account());
    }

    public Account findAccountById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Not found User By Id"));
    }

    public List<String> findAccountLoginsWithoutPrincipal(String login) {
        return accountRepository.findAccountLoginsWithoutPrincipal(login);
    }
}
