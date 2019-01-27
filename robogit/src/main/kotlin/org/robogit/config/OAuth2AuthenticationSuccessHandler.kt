package org.robogit.config

import org.robogit.domain.Role
import org.robogit.domain.User
import org.robogit.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest

@Component
class OAuth2AuthenticationSuccessHandler : AuthenticationSuccessHandler {

  @Autowired
  private var userRepository: UserRepository? = null

  override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse,
                                       authentication: Authentication) {
    if (authentication is OAuth2Authentication && authentication.userAuthentication.details is Map<*, *>) {
      // username is a real name
      val username = (authentication.userAuthentication.details as Map<String, String>)["name"]
      // login is a unique key that identifies the user
      val login = authentication.name
      // register user if does not exist
      if (!userRepository?.existsByLogin(login)!!) {
        val user = User().apply {
          this.login = login
          this.name = username
          this.role = Role.ROLE_USER
        }
        userRepository?.save(user)
      }
    }
    SavedRequestAwareAuthenticationSuccessHandler().onAuthenticationSuccess(request, response, authentication)
  }

}
