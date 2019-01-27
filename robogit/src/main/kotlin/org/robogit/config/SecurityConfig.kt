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
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.web.bind.annotation.RestController
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter
import javax.servlet.Filter
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter


@SpringBootApplication
@EnableOAuth2Client
@RestController
open class SecurityConfig : WebSecurityConfigurerAdapter() {

  @Autowired
  private var oauth2ClientContext: OAuth2ClientContext? = null

  // TODO add redirects
  override fun configure(http: HttpSecurity) {
    http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/login**", "/logout**").permitAll()
        .antMatchers("/api/order/**").hasAnyAuthority(Role.ADMIN.toString(), Role.AUTHORIZED.toString(), Role.EMPLOYEE.toString())
        .antMatchers("/**").hasAnyAuthority(Role.ADMIN.toString(), Role.AUTHORIZED.toString(), Role.EMPLOYEE.toString()) // FIXME
        .antMatchers("/api/card/**").hasAnyAuthority(Role.ADMIN.toString(), Role.AUTHORIZED.toString(), Role.EMPLOYEE.toString())
        .antMatchers("/api/admin/**").hasAnyAuthority(Role.ADMIN.toString())
        .antMatchers("/api/product/**").hasAnyAuthority(Role.ADMIN.toString(), Role.EMPLOYEE.toString())
        .antMatchers("/api/information/**").permitAll()
        .antMatchers("/api/telebot/**").permitAll()
        .and().formLogin().loginPage("/login")
        .and().logout().logoutUrl("/logout")
        .and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter::class.java)
  }

  private fun ssoFilter(): Filter {
    val facebookFilter = OAuth2ClientAuthenticationProcessingFilter("/login/facebook")
    val facebookTemplate = OAuth2RestTemplate(facebook(), oauth2ClientContext)
    facebookFilter.setRestTemplate(facebookTemplate)
    val tokenServices = UserInfoTokenServices(facebookResource().userInfoUri, facebook().clientId)
    tokenServices.setRestTemplate(facebookTemplate)
    facebookFilter.setTokenServices(tokenServices)
    return facebookFilter
  }

  @Bean
  open fun oauth2ClientFilterRegistration(filter: OAuth2ClientContextFilter): FilterRegistrationBean<OAuth2ClientContextFilter> {
    val registration = FilterRegistrationBean<OAuth2ClientContextFilter>()
    registration.filter = filter
    registration.order = -100
    return registration
  }

  @Bean
  @ConfigurationProperties("facebook.client")
  open fun facebook(): AuthorizationCodeResourceDetails {
    return AuthorizationCodeResourceDetails()
  }

  @Bean
  @ConfigurationProperties("facebook.resource")
  open fun facebookResource(): ResourceServerProperties {
    return ResourceServerProperties()
  }

  @Bean(name = [BeanIds.AUTHENTICATION_MANAGER])
  override fun authenticationManagerBean(): AuthenticationManager {
    return super.authenticationManagerBean()
  }
}
