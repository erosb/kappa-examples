package com.github.erosb.kappa.examples;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

class Address {
    @NotBlank
    String streetAddress;
    @Min(1000) @Max(9999)
    Integer postCode;

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public Integer getPostCode() {
        return postCode;
    }

    public void setPostCode(Integer postCode) {
        this.postCode = postCode;
    }
}

class UpdateCustomerRequest {
    List<@NotNull @Valid Address> deliveryAddresses;
    LocalDate birthDate;

    @Size(min = 1)
    public List<Address> getDeliveryAddresses() {
        return deliveryAddresses;
    }

    public void setDeliveryAddresses(List<Address> deliveryAddresses) {
        this.deliveryAddresses = deliveryAddresses;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}

@RestController
public class CustomerController {

    @PutMapping("/customer/{id}")
    public UpdateCustomerRequest updateCustomer(@PathVariable int id, @Valid @RequestBody UpdateCustomerRequest request) {
        return request;
    }
}
