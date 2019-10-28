package io.dmendezg.jwtspringwebflux.config.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import reactor.core.publisher.Mono

class JwtAuthenticationProvider(private val key: String?) : ReactiveAuthenticationManager {
  override fun authenticate(authentication: Authentication?): Mono<Authentication> {
    return try {
      val token = authentication!!.principal as String
      val claims = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(key?.toByteArray())).parseClaimsJws(token)
      Mono.just(JWTAuthenticationToken(claims))
    } catch (e: Exception) {
      Mono.empty()
    }
  }
}