package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDto;
import com.mindhub.homebanking.dtos.ClientDto;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.RandomNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;

    @GetMapping("/accounts")
    public ResponseEntity<?> getAllAccounts() {

        List<AccountDto> accountDtoList = accountService.getListAccountsDto(accountService.getListAccounts());
        if (!accountDtoList.isEmpty()) {
            return new ResponseEntity<>(accountDtoList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There are no Accounts", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Long id) {
        Account account = accountService.getAccountById(id);
        if (account != null) {
            AccountDto accountDto = new AccountDto(account);
            return new ResponseEntity<>(accountDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Account id: " + id + " doesn't exist!", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("clients/accounts/current")
    public ResponseEntity<?> createAccount(Authentication authentication) {

        Client client = clientService.getClientByEmail(authentication.getName());
        //seguir aca , verificar que que no tenga mas de 3 accounts, no se repita el num de account en repositorio de account, crear el num aleatorio y vincular la cuenta con el client.

        if (client.getAccounts().size() >= 3) {
            return new ResponseEntity<>("Client cannot have more than 3 accounts", HttpStatus.FORBIDDEN);
        }

        String accountNumber;
        do {
            accountNumber = "VIN-" + RandomNumber.eightDigits();
        } while (accountService.getAccountByNumber(accountNumber) != null);

        Account account = new Account(accountNumber, 0);
        client.addAccount(account);
        account.setOwner(client);
        accountService.saveAccount(account);

        return new ResponseEntity<>(new ClientDto(client), HttpStatus.CREATED);
    }

    @GetMapping("clients/accounts/current")
    public ResponseEntity<?> getClientAccounts(Authentication authentication) {

        Client client = clientService.getClientByEmail(authentication.getName());
        Set<Account> accountSet = client.getAccounts();
        List<AccountDto> accountDtoList = accountSet.stream()
                .map(AccountDto::new)
                .collect(Collectors.toList());
        if (!accountDtoList.isEmpty()) {
            return new ResponseEntity<>(accountDtoList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There are no Accounts", HttpStatus.NOT_FOUND);
        }
    }
}
