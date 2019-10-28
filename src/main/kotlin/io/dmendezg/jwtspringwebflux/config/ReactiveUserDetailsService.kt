package io.dmendezg.jwtspringwebflux.config

import io.dmendezg.jwtspringwebflux.repository.UserRepository
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ReactiveUserDetailsService(val userRepository: UserRepository) : ReactiveUserDetailsService {
  override fun findByUsername(username: String): Mono<UserDetails> {
    val data = userRepository.findByUsername(username)
    return data.cast(UserDetails::class.java)
  }
}