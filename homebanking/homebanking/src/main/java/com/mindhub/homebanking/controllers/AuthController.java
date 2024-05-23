package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoginDTO;
import com.mindhub.homebanking.dtos.RegisterDTO;
import com.mindhub.homebanking.dtos.ClientDto;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.servicesSecurity.JwtUtilService;
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

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountController accountController;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

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
        if(clientRepository.existsByEmail(registerDTO.email())){
            return  new ResponseEntity<>("The email is already in use", HttpStatus.BAD_REQUEST);
        }

        Client client = new Client(registerDTO.firstName(),registerDTO.lastName(), registerDTO.email(),passwordEncoder.encode(registerDTO.password()));
        clientRepository.save(client);
        return  new ResponseEntity<>("Client created", HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public ResponseEntity<?>test(Authentication authentication){
        String mail = authentication.getName();
        return ResponseEntity.ok("Hello "+mail);
    }

    @GetMapping("/current")
    public ResponseEntity<?> getClient(Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        return ResponseEntity.ok(new ClientDto(client));
    }
}
