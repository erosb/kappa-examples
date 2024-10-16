package com.github.erosb.openapidemo

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.openapi4j.operation.validator.validation.RequestValidator
import org.openapi4j.parser.OpenApi3Parser

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

        RequestValidator(api).validate(org.openapi4j.operation.validator.adapters.server.servlet.ServletRequest.of(req))

        chain.doFilter(req, resp)
    }
}
