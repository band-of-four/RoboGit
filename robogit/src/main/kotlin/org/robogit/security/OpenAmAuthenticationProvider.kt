package org.robogit.security

import org.robogit.domain.Role
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.Arrays
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component


@Component
class OpenAmAuthenticationProvider : AuthenticationProvider {

    private var userDetailsManager: UserDetailsManager? = null

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication {
        if (!userDetailsManager!!.userExists(authentication.getPrincipal() as String)) {
            userDetailsManager!!.createUser(User(authentication.getPrincipal() as String,
                    authentication.getCredentials() as String,
                    Arrays.asList(SimpleGrantedAuthority(Role.AUTHORIZED.toString()))))
        }
        val userDetails = userDetailsManager!!.loadUserByUsername(authentication.getPrincipal() as String)
        val auth = OpenAmAuthenticationToken(authentication.getPrincipal() as String, userDetails.authorities)
        auth.isAuthenticated = true
        auth.details = userDetails
        return auth
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication == OpenAmAuthenticationToken::class.java
    }


    @Autowired
    fun setUserDetailsService(@Qualifier("openAmUserDetailsManager") userDetailsManager: UserDetailsManager) {
        this.userDetailsManager = userDetailsManager
    }
}