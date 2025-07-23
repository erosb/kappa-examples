package com.github.erosb.kappa.examples;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

record UserIdentifier(String id) {
}

record CreatePetRequest(String name, UserIdentifier owner, LocalDate birthDate) {
}

record User(int id, String firstName, String lastName) {
}

record Pet(int id, String name, User owner, long birthDate) {
}

@RestController
@RequestMapping("/api/pets")
public class PetController {

    @PostMapping
    void createPet(@RequestBody CreatePetRequest requestBody) {
        System.out.println("requestBody = " + requestBody);
    }

    @GetMapping
    List<Pet> getPets() {
        return List.of(
            new Pet(
                1,
                "",
                new User(2, "John", "Doe"),
                LocalDate.parse("2017-08-08").toEpochDay()
            )
        );
    }
}
