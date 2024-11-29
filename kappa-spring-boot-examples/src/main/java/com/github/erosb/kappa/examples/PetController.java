package com.github.erosb.kappa.examples;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

class UserIdentifier {
    String id;
}

class CreatePetRequest{
    String name;
    UserIdentifier owner;
    LocalDate birthDate;
}

@RestController
@RequestMapping("/api/pets")
public class PetController {

    @PostMapping
    void createPet(@RequestBody CreatePetRequest requestBody) {
        System.out.println("requestBody = " + requestBody);
    }
}
