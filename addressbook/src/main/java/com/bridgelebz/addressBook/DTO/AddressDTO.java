package com.bridgelebz.addressBook.DTO;


import lombok.Data;

@Data
public class AddressDTO {
    private String fullName;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String phoneNumber;
}