package com.bridgelebz.addressBook.service;

import com.bridgelebz.addressBook.DTO.AddressDTO;
import com.bridgelebz.addressBook.model.Address;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    private final List<Address> addressList = new ArrayList<>();
    private long nextId = 1; // Simple counter for ID generation

    // Get all addresses
    public List<AddressDTO> getAllAddresses() {
        List<AddressDTO> addressDTOs = new ArrayList<>();
        for (Address address : addressList) {
            addressDTOs.add(convertToDTO(address));
        }
        return addressDTOs;
    }

    // Get address by ID
    public Optional<AddressDTO> getAddressById(Long id) {
        for (Address address : addressList) {
            if (address.getId().equals(id)) {
                return Optional.of(convertToDTO(address));
            }
        }
        return Optional.empty();
    }

    // Create a new address
    public AddressDTO createAddress(AddressDTO addressDTO) {
        Address address = convertToEntity(addressDTO);
        address.setId(nextId++); // Assign the next available ID
        addressList.add(address);
        return convertToDTO(address);
    }

    // Update an existing address
    public Optional<AddressDTO> updateAddress(Long id, AddressDTO addressDTO) {
        for (Address address : addressList) {
            if (address.getId().equals(id)) {
                address.setFullName(addressDTO.getFullName());
                address.setAddress(addressDTO.getAddress());
                address.setCity(addressDTO.getCity());
                address.setState(addressDTO.getState());
                address.setZipCode(addressDTO.getZipCode());
                address.setPhoneNumber(addressDTO.getPhoneNumber());
                return Optional.of(convertToDTO(address));
            }
        }
        return Optional.empty();
    }

    // Delete an address by ID
    public boolean deleteAddress(Long id) {
        return addressList.removeIf(address -> address.getId().equals(id));
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