package com.github.erosb.kappa.examples;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PetApiTest {

    @Autowired
    MockMvc mvc;

    @Test
    void test() throws Exception {
        mvc.perform(post("/api/pets")
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
                """))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}
