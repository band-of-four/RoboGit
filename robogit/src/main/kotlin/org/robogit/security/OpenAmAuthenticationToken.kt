package org.robogit.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.authentication.AbstractAuthenticationToken


class OpenAmAuthenticationToken : AbstractAuthenticationToken {
    private var username: String? = null

    constructor(username: String, authorities: Collection<GrantedAuthority>) : super(authorities) {
        this.username = username
    }

    constructor(username: String) : super(null) {
        this.username = username
    }

    override fun getCredentials(): Any {
        return ""
    }

    override fun getPrincipal(): Any? {
        return username
    }
}