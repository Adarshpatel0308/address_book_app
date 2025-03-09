package com.bridgelebz.addressBook.Repository;

import com.bridgelebz.addressBook.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}