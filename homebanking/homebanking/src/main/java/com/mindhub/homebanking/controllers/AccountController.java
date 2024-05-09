package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDto;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/")
    public ResponseEntity<?> getAllAccounts(){
        List<Account> accountList = accountRepository.findAll();
        List<AccountDto> accountDtoList= accountList.stream().map(account -> new AccountDto(account)).collect(Collectors.toList());
        if (!accountList.isEmpty()){
            return new ResponseEntity<>(accountDtoList, HttpStatus.OK);}
        else{
            return new ResponseEntity<>("There are no Accounts",HttpStatus.NOT_FOUND);}
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Long id){
        Account account = accountRepository.findById(id).orElse(null);
        if(account != null){
            AccountDto accountDto = new AccountDto(account);
            return new ResponseEntity<>(accountDto,HttpStatus.OK);}
        else{
            return new ResponseEntity<>("Account id: " + id + " doesn't exist!", HttpStatus.NOT_FOUND);}
    }

}
