package com.bridgelebz.addressBook.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class AddressDTO {
    private Long id;

    @NotBlank(message = "Full name is required") // Ensure the field is not empty
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Full name must contain only alphabets and spaces") // Allow only alphabets and spaces
    private String fullName;

    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String phoneNumber;
}