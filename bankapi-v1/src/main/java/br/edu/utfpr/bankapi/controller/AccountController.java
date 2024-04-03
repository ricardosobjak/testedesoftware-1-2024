package br.edu.utfpr.bankapi.controller;

import br.edu.utfpr.bankapi.contracts.CreateAccountRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateAccountRequest request) {

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Object> get() {
        return ResponseEntity.ok().body("Hello World");
    }

}
