package com.bridgelebz.addressBook.controller;

import com.bridgelebz.addressBook.Repository.AddressRepository;
import com.bridgelebz.addressBook.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/addresses")
public class AddressController {
    @Autowired
    private AddressRepository repository;

    @GetMapping
    public ResponseEntity<List<Address>> getAll() {
        List<Address> addresses = repository.findAll();
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getById(@PathVariable Long id) {
        Optional<Address> address = repository.findById(id);
        return address.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Address> create(@RequestBody Address address) {
        Address savedAddress = repository.save(address);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAddress);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> update(@PathVariable Long id, @RequestBody Address address) {
        return repository.findById(id).map(existing -> {
            existing.setFullName(address.getFullName());
            existing.setAddress(address.getAddress());
            existing.setCity(address.getCity());
            existing.setState(address.getState());
            existing.setZipCode(address.getZipCode());
            existing.setPhoneNumber(address.getPhoneNumber());
            return ResponseEntity.ok(repository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}