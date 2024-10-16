package com.github.erosb.openapidemo

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

data class CreatePetRequest(var name: String)

@RestController
@RequestMapping("/api/pet")
class PetController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createPet() {

    }
}
