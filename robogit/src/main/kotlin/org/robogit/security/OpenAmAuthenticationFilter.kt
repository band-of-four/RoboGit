package org.robogit.security

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.postgresql.gss.MakeGSS.authenticate
import org.robogit.config.SecurityConfig
import org.springframework.http.*
import org.springframework.security.core.Authentication
import org.springframework.util.ClassUtils.isPresent
import java.util.Arrays
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import javax.servlet.ServletException
import java.io.IOException
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.stereotype.Component
import javax.servlet.http.Cookie


@Component
class OpenAmAuthenticationFilter internal constructor() : AbstractAuthenticationProcessingFilter("/login") {

    @Throws(AuthenticationException::class, IOException::class, ServletException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication? {
        val iPlanetDirectoryPro = Arrays.stream<Cookie>(request.cookies)
                .filter { c -> c.getName() == "iPlanetDirectoryPro" }.findFirst()

        val savedRequest = HttpSessionRequestCache().getRequest(request, response)
        val redirectUrl = if (savedRequest == null) SecurityConfig.HOME_PAGE_URL else savedRequest.redirectUrl

        if (!iPlanetDirectoryPro.isPresent()) {
            response.sendRedirect("${SecurityConfig.OPENAM_LOGIN_URL}&goto=$redirectUrl")
            return null
        } else {
            val headers = HttpHeaders()
            headers.add("cookie", iPlanetDirectoryPro.get().getName() + "=" + iPlanetDirectoryPro.get().getValue())
            val entity:HttpEntity<Any?> = HttpEntity(headers)
            val restTemplate = RestTemplate()
            val attributesResponse: ResponseEntity<OpenAmAttributeResponse>?
            try {
                attributesResponse = restTemplate.exchange(SecurityConfig.OPENAM_ATTRIBUTES_URL, HttpMethod.GET, entity, OpenAmAttributeResponse::class.java)
            } catch (e: HttpClientErrorException) {
                if (e.statusCode == HttpStatus.UNAUTHORIZED) {
                    response.sendRedirect("${SecurityConfig.OPENAM_LOGIN_URL}&goto=$redirectUrl")
                    return null
                } else {
                    //Redirect to home page
                    response.sendRedirect(SecurityConfig.HOME_PAGE_URL)
                    return null
                }
            }

            if (attributesResponse != null && attributesResponse.hasBody()) {
                val username = Arrays.stream(attributesResponse.body!!.attributes!!)
                        .filter { a -> a.name == "uid" }
                        .findFirst()
                        .map<Array<String>>({it.values })
                        .map<String> { v -> v[0] }

                if (username.isPresent) {
                    val authRequest = OpenAmAuthenticationToken(username.get())
                    return this.authenticationManager.authenticate(authRequest)
                }

            }

            throw UsernameNotFoundException("Can't get username")

        }

    }

    @Autowired
    override fun setAuthenticationManager(authenticationManager: AuthenticationManager) {
        super.setAuthenticationManager(authenticationManager)
    }


    class OpenAmAttributeResponse {
        var attributes: Array<OpenAmAttribute>? = null
            set(attributes) {
                field = this.attributes
            }
    }

    class OpenAmAttribute {
        var name: String? = null
            set(name) {
                field = this.name
            }
        var values: Array<String>? = null
            set(values) {
                field = this.values
            }
    }
}