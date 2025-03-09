package com.bridgelebz.addressBook.service;

import com.bridgelebz.addressBook.DTO.AddressDTO;
import com.bridgelebz.addressBook.exception.AddressNotFoundException;
import com.bridgelebz.addressBook.model.Address;
import com.bridgelebz.addressBook.Repository.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    // Get all addresses
    public List<AddressDTO> getAllAddresses() {
        log.info("Fetching all addresses");
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get address by ID
    public AddressDTO getAddressById(Long id) {
        log.info("Fetching address with id: {}", id);
        Optional<Address> address = addressRepository.findById(id);
        if (address.isPresent()) {
            return convertToDTO(address.get());
        } else {
            throw new AddressNotFoundException("Address not found with ID: " + id);
        }
    }

    // Create a new address
    @Transactional
    public AddressDTO createAddress(AddressDTO addressDTO) {
        log.info("Creating new address: {}", addressDTO);
        Address address = convertToEntity(addressDTO);
        try {
            address = addressRepository.save(address); // Save to database
            return convertToDTO(address);
        } catch (OptimisticLockingFailureException e) {
            log.error("Concurrent modification detected while creating address: {}", addressDTO);
            throw new RuntimeException("Concurrent modification detected. Please retry.");
        }
    }

    // Update an existing address
    @Transactional
    public AddressDTO updateAddress(Long id, AddressDTO addressDTO) {
        log.info("Updating address with id: {}", id);
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()) {
            Address address = optionalAddress.get();
            address.setFullName(addressDTO.getFullName());
            address.setAddress(addressDTO.getAddress());
            address.setCity(addressDTO.getCity());
            address.setState(addressDTO.getState());
            address.setZipCode(addressDTO.getZipCode());
            address.setPhoneNumber(addressDTO.getPhoneNumber());
            try {
                address = addressRepository.save(address); // Save updated address to database
                return convertToDTO(address);
            } catch (OptimisticLockingFailureException e) {
                log.error("Concurrent modification detected while updating address with id: {}", id);
                throw new RuntimeException("Concurrent modification detected. Please retry.");
            }
        } else {
            throw new AddressNotFoundException("Address not found with ID: " + id);
        }
    }

    // Delete an address by ID
    @Transactional
    public void deleteAddress(Long id) {
        log.info("Deleting address with id: {}", id);
        if (addressRepository.existsById(id)) {
            try {
                addressRepository.deleteById(id);
            } catch (OptimisticLockingFailureException e) {
                log.error("Concurrent modification detected while deleting address with id: {}", id);
                throw new RuntimeException("Concurrent modification detected. Please retry.");
            }
        } else {
            throw new AddressNotFoundException("Address not found with ID: " + id);
        }
    }

    // Helper methods to convert between DTO and Entity
    private AddressDTO convertToDTO(Address address) {
        AddressDTO dto = new AddressDTO();
        dto.setId(address.getId());
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
        // Do not set the ID manually; let the database generate it
        address.setFullName(dto.getFullName());
        address.setAddress(dto.getAddress());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZipCode(dto.getZipCode());
        address.setPhoneNumber(dto.getPhoneNumber());
        return address;
    }
}