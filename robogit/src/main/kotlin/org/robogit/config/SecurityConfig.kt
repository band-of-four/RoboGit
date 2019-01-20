package org.robogit.config

import org.robogit.domain.Role
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.context.annotation.Bean
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter



@EnableWebSecurity
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
open class SecurityConfig : WebSecurityConfigurerAdapter() {

    companion object {
        const val OPENAM_LOGOUT_URL = "http://localhost:8888/openam/XUI/#logout/"
        const val OPENAM_LOGIN_URL = "http://localhost:8888/openam/XUI/#login/"
        const val OPENAM_ATTRIBUTES_URL = "http://localhost:8888/openam/identity/json/attributes"
        const val HOME_PAGE_URL = "http://localhost:8080/"
    }

    @Autowired
    private val authenticationProcessingFilter: AbstractAuthenticationProcessingFilter? = null
    @Autowired
    private val logoutSuccessHandler: LogoutSuccessHandler? = null

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
                .addFilterAt(authenticationProcessingFilter!!, UsernamePasswordAuthenticationFilter::class.java)
                .authorizeRequests()
                .antMatchers("/login", "/logout").permitAll()
                .antMatchers("/api/order/**").hasAnyAuthority(Role.ADMIN.toString(), Role.AUTHORIZED.toString(), Role.EMPLOYEE.toString())
                .antMatchers("/api/admin/**").hasAnyAuthority(Role.ADMIN.toString())
                .antMatchers("/api/information/**").permitAll()
                .and().formLogin().loginPage("/login")
                .and().logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler)
    }

    @Bean(name = arrayOf(BeanIds.AUTHENTICATION_MANAGER))
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }
}