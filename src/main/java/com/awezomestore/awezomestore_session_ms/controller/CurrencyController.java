package com.awezomestore.awezomestore_session_ms.controller;

import com.awezomestore.awezomestore_session_ms.dto.CurrencyDTO;
import com.awezomestore.awezomestore_session_ms.service.CurrencyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/currency")
public class CurrencyController {

    @Autowired
    private CurrencyService service;

    @GetMapping(value = "/getAll")
    public ResponseEntity getAll(){
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity create(@RequestBody CurrencyDTO currency){
        return new ResponseEntity<>(service.create(currency), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/update")
    public ResponseEntity update(@PathVariable(value = "id") String id, @RequestBody CurrencyDTO currency){
        return new ResponseEntity<>(service.update(id, currency), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity delete(@PathVariable(value = "id") String id){
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }

}
