package io.dmendezg.jwtspringwebflux

import io.dmendezg.jwtspringwebflux.model.document.User
import io.dmendezg.jwtspringwebflux.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class Initializer(val userRepository: UserRepository) : CommandLineRunner {
  @Autowired
  fun passswordEncoder() = BCryptPasswordEncoder()

  override fun run(vararg args: String?) {
    val username = "hendra"
    val account = User(null, username, passswordEncoder().encode("1234"), "ADMIN")
    userRepository.findByUsername("hendra").switchIfEmpty(userRepository.save(account)).subscribe()
  }
}