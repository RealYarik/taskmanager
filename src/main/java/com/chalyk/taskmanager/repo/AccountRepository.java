package com.chalyk.taskmanager.repo;

import com.chalyk.taskmanager.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findAccountByLogin(String login);

    @Query("SELECT a.login FROM Account a WHERE a.login <> :login ORDER BY a.login ASC")
    List<String> findAccountLoginsWithoutPrincipal(@Param("login") String login);
}
