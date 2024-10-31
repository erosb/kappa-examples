package com.github.erosb.openapidemo

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate


data class UserIdentifier(
    var id: Int
)

data class CreatePetRequest(
    var name: String,
    var owner: UserIdentifier,
    var birthDate: LocalDate
)

@RestController
@RequestMapping("/api/pet")
class PetController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createPet(@RequestBody request: CreatePetRequest): Any {
        return request
    }
}
