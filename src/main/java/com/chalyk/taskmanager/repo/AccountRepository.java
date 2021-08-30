package com.chalyk.taskmanager.repo;

import com.chalyk.taskmanager.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findAccountByLogin(String login);
}
