package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDto;
import com.mindhub.homebanking.dtos.ClientDto;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
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
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/accounts")
    public ResponseEntity<?> getAllAccounts(){
        List<Account> accountList = accountRepository.findAll();
        List<AccountDto> accountDtoList= accountList.stream().map(account -> new AccountDto(account)).collect(Collectors.toList());
        if (!accountList.isEmpty()){
            return new ResponseEntity<>(accountDtoList, HttpStatus.OK);}
        else{
            return new ResponseEntity<>("There are no Accounts",HttpStatus.NOT_FOUND);}
    }
    @GetMapping("/accounts/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Long id){
        Account account = accountRepository.findById(id).orElse(null);
        if(account != null){
            AccountDto accountDto = new AccountDto(account);
            return new ResponseEntity<>(accountDto,HttpStatus.OK);}
        else{
            return new ResponseEntity<>("Account id: " + id + " doesn't exist!", HttpStatus.NOT_FOUND);}
    }
    @PostMapping("clients/accounts/current")
    public ResponseEntity<?> createAccount(Authentication authentication){

        Client client = clientRepository.findByEmail(authentication.getName());
        //seguir aca , verificar que que no tenga mas de 3 accounts, no se repita el num de account en repositorio de account, crear el num aleatorio y vincular la cuenta con el client.

        if(client.getAccounts().size() >= 3){
            return new ResponseEntity<>("Client cannot have more than 3 accounts", HttpStatus.FORBIDDEN);
        }

        String accountNumber;
        do {
            accountNumber = "VIN-"+RandomNumber.eightDigits();
        } while (accountRepository.findByNumber(accountNumber) != null);

        Account account = new Account(accountNumber,0);
        client.addAccount(account);
        account.setOwner(client);
        accountRepository.save((account));
        clientRepository.save(client);

        return new ResponseEntity<>(new ClientDto(client), HttpStatus.CREATED);
    }

    @GetMapping("clients/accounts/current")
    public ResponseEntity<?> getClientAccounts(Authentication authentication){

        Client client = clientRepository.findByEmail(authentication.getName());
        Set<Account> accountSet = client.getAccounts();
        List<AccountDto> accountDtoList= accountSet.stream()
                .map(AccountDto::new)
                .collect(Collectors.toList());
        if (!accountDtoList.isEmpty()){
            return new ResponseEntity<>(accountDtoList, HttpStatus.OK);}
        else{
            return new ResponseEntity<>("There are no Accounts",HttpStatus.NOT_FOUND);}
    }
}
