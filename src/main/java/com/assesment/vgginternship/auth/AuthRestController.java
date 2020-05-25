package com.assesment.vgginternship.auth;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthRepository authRepository;
    
    @GetMapping
    public Iterable<AuthEntity> list() {
        return authRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AuthEntity> get(@PathVariable Long id) {
        Optional<AuthEntity> auth = authRepository.findById(id);
        if (auth.isPresent()) {
            return new ResponseEntity<>(auth.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable Long id, @RequestBody AuthEntity auth) {
        Optional<AuthEntity> authEntity = authRepository.findById(id);
        if (authEntity.isPresent()) {
            auth.setId(authEntity.get().getId());
            return new ResponseEntity<>(authRepository.save(auth), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthEntity post(@RequestBody AuthEntity auth) {
        String password = passwordEncoder.encode(auth.getPassword());
        auth.setPassword(password);
        return authRepository.save(auth);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        try {
            authRepository.findById(id);
        } catch (EmptyResultDataAccessException ex) {}
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Error message")
    public void handleError() {
    }
    
}
