package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDto;
import com.mindhub.homebanking.models.Account;
import java.util.List;

public interface AccountService {

    List<Account> getListAccounts();

    List<AccountDto> getListAccountsDto(List <Account> accounts);

    Account getAccountById(Long id);

    AccountDto getAccountDto(Account account);

    Account getAccountByNumber(String number);

    boolean existsAccountByNumber(String number);

    void saveAccount(Account account);
}
