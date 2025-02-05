package com.github.erosb.kappa.examples;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
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
        var validationErrors = mvc.perform(post("/api/pets")
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
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        JSONAssert.assertEquals("""
                {
                  "errors": [
                    {
                      "dataLocation": "$request.body#/type",
                      "schemaLocation": "openapi/pets-api.yaml#/components/schemas/CreatePetRequest/additionalProperties",
                      "dynamicPath": "#/$ref/additionalProperties/false",
                      "message": "false schema always fails"
                    },
                    {
                      "dataLocation": "$request.body#/name",
                      "schemaLocation": "openapi/pets-api.yaml#/components/schemas/Name/type",
                      "dynamicPath": "#/$ref/properties/name/$ref/type",
                      "message": "expected type: string, actual: null"
                    },
                    {
                      "dataLocation": "$request.body#/owner/id",
                      "schemaLocation": "openapi/common-types.yaml#/Id",
                      "dynamicPath": "#/$ref/properties/owner/$ref/properties/id/$ref/minimum",
                      "message": "-5 is lower than minimum 0"
                    },
                    {
                      "dataLocation": "$request.body#/birthDate",
                      "schemaLocation": "openapi/pets-api.yaml#/components/schemas/CreatePetRequest/properties/birthDate/format",
                      "dynamicPath": "#/$ref/properties/birthDate/format",
                      "message": "instance does not match format 'date'"
                    }
                  ]
                }
                """, validationErrors, true);
    }
}
