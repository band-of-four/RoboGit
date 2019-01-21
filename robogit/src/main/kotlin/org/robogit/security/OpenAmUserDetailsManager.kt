package org.robogit.security

import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.provisioning.UserDetailsManager
import lombok.extern.slf4j.Slf4j
import org.robogit.domain.Role
import org.robogit.domain.User
import org.robogit.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.util.*


@Component
class OpenAmUserDetailsManager : UserDetailsManager {
    @Autowired
    val userRepository: UserRepository? = null

    override fun createUser(userDetails: UserDetails) {
        val user = User();
        user.login = userDetails.username
        user.password = "pswd"
        user.role = Role.AUTHORIZED
        userRepository?.save(user)
    }

    override fun updateUser(user: UserDetails) {

    }

    override fun deleteUser(username: String) {

    }

    override fun changePassword(oldPassword: String, newPassword: String) {

    }

    override fun userExists(username: String): Boolean {
        return userRepository?.findByLogin(username) != null
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails? {
        println("MANAGER")
        val user = userRepository?.findByLogin(username)
        val userDetails = OpenAmUserDetails(user?.login, user?.password, Arrays.asList(SimpleGrantedAuthority(Role.AUTHORIZED.toString())), user?.id!!)
        return userDetails
    }
}