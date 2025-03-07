package com.addressapp.addressbook.controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AddressBookController {

    @GetMapping
    public String getAllContacts() {
        return "Get all contacts";
    }

    @GetMapping("/{id}")
    public String getContactById(@PathVariable Long id) {
        return "Get contact by ID: " + id;
    }

    @PostMapping
    public String addContact() {
        return "Add a new contact";
    }

    @PutMapping("/{id}")
    public String updateContact(@PathVariable Long id) {
        return "Update contact with ID: " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteContact(@PathVariable Long id) {
        return "Delete contact with ID: " + id;
    }
}
