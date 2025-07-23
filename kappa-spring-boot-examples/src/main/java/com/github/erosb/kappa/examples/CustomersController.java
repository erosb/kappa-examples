package com.github.erosb.kappa.examples;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomersController {


    @PutMapping("/{id}/addresses")
    public void updateAddresses(@PathVariable int id, @RequestBody List<Object> body) {

    }
}
