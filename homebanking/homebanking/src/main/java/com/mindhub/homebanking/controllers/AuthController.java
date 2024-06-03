package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoginDTO;
import com.mindhub.homebanking.dtos.RegisterDTO;
import com.mindhub.homebanking.dtos.ClientDto;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.servicesSecurity.JwtUtilService;
import com.mindhub.homebanking.utils.RandomNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginDTO loginDTO){
    try{
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.email(),loginDTO.password()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.email());
        final String jwt = jwtUtilService.generateToken(userDetails);
        return ResponseEntity.ok(jwt);
        }catch (Exception e){
        return  new ResponseEntity<>("Email or password invalid" , HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity <?> register (@RequestBody RegisterDTO registerDTO){
        System.out.println("RegisterDTO: " + registerDTO);
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        // se crea un objeto pattern ( compila la expresión regular proporcionada)
        Pattern pattern = Pattern.compile(emailRegex);

        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        Pattern passwordPattern = Pattern.compile(passwordRegex);

        if(registerDTO.firstName().isBlank()){
            return  new ResponseEntity<>("The name field must not be empty", HttpStatus.FORBIDDEN);
        }
        if(registerDTO.lastName().isBlank()){
            return  new ResponseEntity<>("The surname field must not be empty", HttpStatus.FORBIDDEN);
        }
        if(registerDTO.email().isBlank()){
            return  new ResponseEntity<>("The email field must not be empty", HttpStatus.FORBIDDEN);
        }
        if(registerDTO.password().isBlank()){
            return  new ResponseEntity<>("The password field must not be empty", HttpStatus.FORBIDDEN);
        }
        if(!passwordPattern.matcher(registerDTO.password()).matches()){
            return  new ResponseEntity<>("The password field must have 8 characters one uppercase one lowercase", HttpStatus.BAD_REQUEST);
        }
        if(clientService.existsClientByEmail(registerDTO.email())){
            return  new ResponseEntity<>("The email is already in use", HttpStatus.BAD_REQUEST);
        }
        /*if(clientRepository.findByEmail(registerDTO.email()) != null){
            return  new ResponseEntity<>("The email is already in use", HttpStatus.BAD_REQUEST);
        }**/
        if (!pattern.matcher(registerDTO.email()).matches()) {
            return new ResponseEntity<>("The email format is incorrect", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(registerDTO.firstName(),registerDTO.lastName(), registerDTO.email(),passwordEncoder.encode(registerDTO.password()));
        System.out.println("Creating new client: " + client);
        String accountNumber;
        do {
            accountNumber = "VIN-"+ RandomNumber.eightDigits();
        } while (accountService.existsAccountByNumber(accountNumber));

        Account account = new Account(accountNumber,0);

        client.addAccount(account);
        account.setOwner(client);
       clientService.saveClient(client);
        accountService.saveAccount(account);

        return  new ResponseEntity<>("Client created", HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public ResponseEntity<?>test(Authentication authentication){
        String mail = authentication.getName();
        return ResponseEntity.ok("Hello "+mail);
    }

    @GetMapping("/current")
    public ResponseEntity<?> getClient(Authentication authentication){
        Client client = clientService.getClientByEmail(authentication.getName());
        return ResponseEntity.ok(new ClientDto(client));
    }
}
