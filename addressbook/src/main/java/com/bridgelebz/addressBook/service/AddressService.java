package com.bridgelebz.addressBook.service;


import com.bridgelebz.addressBook.DTO.AddressDTO;
import com.bridgelebz.addressBook.model.Address;
import com.bridgelebz.addressBook.Repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressService {
    @Autowired
    private AddressRepository repository;

    public List<AddressDTO> getAllAddresses() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<AddressDTO> getAddressById(Long id) {
        return repository.findById(id)
                .map(this::convertToDTO);
    }

    public AddressDTO createAddress(AddressDTO addressDTO) {
        Address address = convertToEntity(addressDTO);
        Address savedAddress = repository.save(address);
        return convertToDTO(savedAddress);
    }

    public Optional<AddressDTO> updateAddress(Long id, AddressDTO addressDTO) {
        return repository.findById(id).map(existing -> {
            existing.setFullName(addressDTO.getFullName());
            existing.setAddress(addressDTO.getAddress());
            existing.setCity(addressDTO.getCity());
            existing.setState(addressDTO.getState());
            existing.setZipCode(addressDTO.getZipCode());
            existing.setPhoneNumber(addressDTO.getPhoneNumber());
            Address updatedAddress = repository.save(existing);
            return convertToDTO(updatedAddress);
        });
    }

    public boolean deleteAddress(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
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
