package com.github.erosb.openapidemo

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.erosb.kappa.core.validation.ValidationException
import com.github.erosb.kappa.operation.validator.adapters.server.servlet.JakartaServletRequest
import com.github.erosb.kappa.operation.validator.adapters.server.servlet.MemoizingServletRequest
import com.github.erosb.kappa.operation.validator.validation.RequestValidator
import com.github.erosb.kappa.parser.OpenApi3Parser
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

class OpenApiBackedRequestValidationFilter : Filter {
    val api = OpenApi3Parser().parse(javaClass.getResource("/openapi/pets-api.yaml"), false)

    override fun doFilter(
        req: ServletRequest?,
        resp: ServletResponse?,
        chain: FilterChain?,
    ) {
        req as HttpServletRequest
        resp as HttpServletResponse
        chain as FilterChain

        try {
            val cachedReq = MemoizingServletRequest(req)
            RequestValidator(api).validate(JakartaServletRequest.of(cachedReq))
            chain.doFilter(cachedReq, resp)
        } catch (ex: ValidationException) {
            val objectMapper = ObjectMapper()
            val respObj = objectMapper.createObjectNode()
            val itemsJson = objectMapper.createArrayNode()
            ex.results().forEach { item ->
                val itemJson = objectMapper.createObjectNode()

                itemJson.put("dataLocation", item.describeInstanceLocation())
                itemJson.put("schemaLocation", item.describeSchemaLocation());
                itemJson.put("message", item.message)
                itemsJson.add(itemJson)
            }
            respObj.put("errors", itemsJson)
            resp.status = 400
            resp.writer.print(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(respObj))
        }
    }
}
