package org.robogit.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class OpenAmUserDetails(username: String?, password: String?, authorities: MutableCollection<out GrantedAuthority>?, val userId: Int) : User(username, password, authorities) {

}