package io.dmendezg.jwtspringwebflux.handler

import io.dmendezg.jwtspringwebflux.model.Login
import io.dmendezg.jwtspringwebflux.model.Token
import io.dmendezg.jwtspringwebflux.repository.UserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.util.*

@Service
class LoginHandler {

  @Value("\${key}")
  private val key: String? = null

  @Autowired
  lateinit var userRepository: UserRepository

  @Bean
  fun passwordEncoder() = BCryptPasswordEncoder()

  private fun createToken(username: String, expiresIn: Int): String {
    val now = System.currentTimeMillis()
    val exp = now + expiresIn * 1000
    val compactJws = Jwts.builder()
        .setIssuedAt(Date(now))
        .setExpiration(Date(exp))
        .setSubject(username)
        .signWith(Keys.hmacShaKeyFor(key?.toByteArray()), SignatureAlgorithm.HS512)
        .compact()
    return compactJws
  }

  fun token(serverRequest: ServerRequest): Mono<ServerResponse> {
    return serverRequest.bodyToMono(Login::class.java).flatMap { login ->
      userRepository.findByUsername(login.username).flatMap { user ->
        if (passwordEncoder().matches(login.password, user.password)) {
          ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(Token(createToken(login.username, 7200))))
        } else {
          ServerResponse.badRequest().build()
        }
      }.switchIfEmpty(ServerResponse.badRequest().build())
    }
  }

  fun secure(serverRequest: ServerRequest): Mono<ServerResponse> {
    return serverRequest.principal().flatMap {
      ServerResponse.ok().body(BodyInserters.fromValue("Hello ${it.name}!"))
    }
  }

}