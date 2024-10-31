package com.github.erosb.openapidemo

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
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
                    "name": null,
                    "type": "cat",
                    "owner": {
                        "id": -5
                    },
                    "birthDate": "2023-07----08"
                }
            """.trimIndent()))
            .andExpect(status().isCreated)
    }

    @Test
    fun success() {
        mvc.perform(MockMvcRequestBuilders.post("/api/pet")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "name": "Adele Blanka",
                    "owner": {
                        "id": 1
                    },
                    "birthDate": "2023-07-08"
                }
            """.trimIndent()))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.name").value("Adele Blanka"))
            .andExpect(jsonPath("$.birthDate").value("2023-07-08"))
    }

    @Test
    fun `malformed json`() {
        mvc.perform(MockMvcRequestBuilders.post("/api/pet")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content("""
                000{
                        "id": -5
                    }
                }
            """.trimIndent()))
            .andExpect(status().isCreated)
    }
}
