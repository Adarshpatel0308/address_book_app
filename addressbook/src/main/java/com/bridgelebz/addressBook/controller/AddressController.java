package com.bridgelebz.addressBook.controller;

import com.bridgelebz.addressBook.DTO.AddressDTO;
import com.bridgelebz.addressBook.model.Address;
import com.bridgelebz.addressBook.Repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/addresses")
public class AddressController {
    @Autowired
    private AddressRepository repository;

    @GetMapping
    public ResponseEntity<List<AddressDTO>> getAll() {
        List<Address> addresses = repository.findAll();
        List<AddressDTO> addressDTOs = addresses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(addressDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getById(@PathVariable Long id) {
        Optional<Address> address = repository.findById(id);
        return address.map(a -> ResponseEntity.ok(convertToDTO(a)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AddressDTO> create(@RequestBody AddressDTO addressDTO) {
        Address address = convertToEntity(addressDTO);
        Address savedAddress = repository.save(address);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedAddress));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> update(@PathVariable Long id, @RequestBody AddressDTO addressDTO) {
        return repository.findById(id).map(existing -> {
            existing.setFullName(addressDTO.getFullName());
            existing.setAddress(addressDTO.getAddress());
            existing.setCity(addressDTO.getCity());
            existing.setState(addressDTO.getState());
            existing.setZipCode(addressDTO.getZipCode());
            existing.setPhoneNumber(addressDTO.getPhoneNumber());
            Address updatedAddress = repository.save(existing);
            return ResponseEntity.ok(convertToDTO(updatedAddress));
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

    // Helper methods to convert between DTO and Entity
    private AddressDTO convertToDTO(Address address) {
        AddressDTO dto = new AddressDTO();
        dto.setFullName(address.getFullName());
        dto.setAddress(address.getAddress());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setZipCode(address.getZipCode());
        dto.setPhoneNumber(address.getPhoneNumber());
        return dto;
    }

    private Address convertToEntity(AddressDTO dto) {
        Address address = new Address();
        address.setFullName(dto.getFullName());
        address.setAddress(dto.getAddress());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZipCode(dto.getZipCode());
        address.setPhoneNumber(dto.getPhoneNumber());
        return address;
    }
}