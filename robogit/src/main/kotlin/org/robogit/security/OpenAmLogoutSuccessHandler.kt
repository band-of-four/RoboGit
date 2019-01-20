//package org.robogit.security
//
////import org.robogit.config.SecurityConfig
//import org.springframework.security.core.Authentication
//import javax.servlet.ServletException
//import java.io.IOException
//import javax.servlet.http.HttpServletResponse
//import javax.servlet.http.HttpServletRequest
//import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
//import org.springframework.stereotype.Component
//
//
//@Component
//class OpenAmLogoutSuccessHandler : LogoutSuccessHandler {
//    @Throws(IOException::class, ServletException::class)
//    override fun onLogoutSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
//        response.sendRedirect("${SecurityConfig.OPENAM_LOGOUT_URL}&goto=${SecurityConfig.HOME_PAGE_URL}")
//    }
//}