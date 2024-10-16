package com.github.erosb.openapidemo

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class ApiControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    @Test
    fun test() {
        mvc.perform(MockMvcRequestBuilders.post("/api/pet")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "name": "Todor"
                }
            """.trimIndent()))
            .andExpect(status().isCreated)

    }
}
