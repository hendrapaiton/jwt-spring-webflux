package io.dmendezg.jwtspringwebflux.router

import io.dmendezg.jwtspringwebflux.handler.LoginHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
class LoginRouter {

  @Bean
  fun routes(loginHandler: LoginHandler) = router {
    ("" and accept(MediaType.APPLICATION_JSON)).nest {
      POST("/oauth/token", loginHandler::token)
      GET("/secure", loginHandler::secure)
    }
  }

}

