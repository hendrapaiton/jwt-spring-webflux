package io.dmendezg.jwtspringwebflux.repository

import io.dmendezg.jwtspringwebflux.model.document.User
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface UserRepository : ReactiveMongoRepository<User, String> {
  fun findByUsername(username: String?): Mono<User>
}