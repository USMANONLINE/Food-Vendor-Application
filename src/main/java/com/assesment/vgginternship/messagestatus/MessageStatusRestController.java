package com.assesment.vgginternship.messagestatus;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api/messagestatus")
public class MessageStatusRestController {
    @Autowired
    private MessageStatusEntityRepository messageStatusRepository;
    
    @GetMapping
    public Iterable<MessageStatusEntity> list() {
        return messageStatusRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MessageStatusEntity> get(@PathVariable Long id) {
        Optional<MessageStatusEntity> messageStatus = messageStatusRepository.findById(id);
        if (messageStatus.isPresent()) {
            return new ResponseEntity<>(messageStatus.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<MessageStatusEntity> put(@PathVariable Long id, @RequestBody MessageStatusEntity messageStatus) {
        Optional<MessageStatusEntity> messageStatusEntity = messageStatusRepository.findById(id);
        if (messageStatusEntity.isPresent()) {
            messageStatus.setId(messageStatusEntity.get().getId());
            return new ResponseEntity<>(messageStatusRepository.save(messageStatus), HttpStatus.OK);
        }
        return new ResponseEntity<>(messageStatusRepository.save(messageStatus), HttpStatus.CREATED);
    }
    
    @PatchMapping("/{id}")
    public MessageStatusEntity patch (@PathVariable Long id, @RequestBody MessageStatusEntity patch) {
        MessageStatusEntity messageStatus = messageStatusRepository.findById(id).get();
        if (patch.getId() != null) {
            messageStatus.setId(patch.getId());
        }
        if (patch.getName() != null) {
            messageStatus.setName(patch.getName());
        }
        return messageStatusRepository.save(messageStatus);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageStatusEntity post(@RequestBody MessageStatusEntity messageStatus) {
        return messageStatusRepository.save(messageStatus);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        try {
            messageStatusRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {}
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Error message")
    public void handleError() {
    }
    
}
