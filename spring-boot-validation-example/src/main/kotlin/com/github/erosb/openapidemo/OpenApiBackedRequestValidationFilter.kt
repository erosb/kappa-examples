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
import jakarta.servlet.annotation.WebFilter
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
            // we need to wrap the original request instance into a MemoizingServletRequest,
            // since we will need to parse the request body twice: once for the OpenAPI-validation
            // and once for the jackson parsing.
            // basic HttpServletRequests cannot be read twice, hence we use the MemoizingServletRequest shipped with Kappa
            // more here: https://www.baeldung.com/spring-reading-httpservletrequest-multiple-times
            val memoizedReq = MemoizingServletRequest(req)

            // Kappa can understand different representations of HTTP requests and responses
            // here we use the Servlet API specific adapter of Kappa, to get a Kappa Request instance
            // which wraps a HttpServletRequest
            val jakartaRequest = JakartaServletRequest.of(memoizedReq)

            // we do the validation
            RequestValidator(api).validate(jakartaRequest)

            // if no request validation error was found, we proceed with the request execution
            chain.doFilter(memoizedReq, resp)
        } catch (ex: ValidationException) {
            // if the request validation failed, we represents the validation failures in a simple
            // json response and send it back to the client
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
