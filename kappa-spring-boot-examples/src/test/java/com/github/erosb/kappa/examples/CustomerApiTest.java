package com.github.erosb.kappa.examples;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerApiTest {

    @Autowired
    MockMvc mvc;

    @Test
    public void wrongMethod()
            throws Exception {
        mvc.perform(post("/customer/22")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().string(""));
    }

    @Test
    public void pathNotFound()
            throws Exception {
        mvc.perform(put("/customer/22/extraneous")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    public void wrongContentType()
            throws Exception {
        mvc.perform(put("/customer/22")
                .contentType(MediaType.APPLICATION_ATOM_XML)
        ).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void wrongPathParameterType()
            throws Exception {
        mvc.perform(put("/customer/undefined")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void malformedBody()
            throws Exception {
        mvc.perform(put("/customer/22")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("[[[{")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    public void addressNotArray()
            throws Exception {
        mvc.perform(put("/customer/22")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "deliveryAddresses": {},
                                    "birthDate": "2020-03-04"
                                }
                                """)
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    public void nullAddress()
            throws Exception {
        mvc.perform(put("/customer/22")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "deliveryAddresses": [null],
                                    "birthDate": "2020-03-04"
                                }
                                """)
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    public void tooLowPostCode()
            throws Exception {
        mvc.perform(put("/customer/22")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "deliveryAddresses": [
                                        {
                                            "streetAddress": "1234 Paper Street",
                                            "postCode": 12356
                                        }
                                    ],
                                    "birthDate": "2020-03-04"
                                }
                                """)
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    public void emptyAddressList()
            throws Exception {
        mvc.perform(put("/customer/22")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "deliveryAddresses": [],
                                    "birthDate": "2020-03-04"
                                }
                                """)
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    public void nullStreetAddress()
            throws Exception {
        mvc.perform(put("/customer/22")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "deliveryAddresses": [
                                        {
                                            "streetAddress": null,
                                            "postCode": 12356
                                        }
                                    ],
                                    "birthDate": "2020-03-04"
                                }
                                """)
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    public void success()
            throws Exception {
        mvc.perform(put("/customer/22")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "deliveryAddresses": [
                                {
                                    "streetAddress": "1234 Paper Street",
                                    "postCode": "4567"
                                }
                            ],
                            "birthDate": "2020-03-04"
                        }
                        """)
        ).andDo(MockMvcResultHandlers.print());
    }
}
