package io.dmendezg.jwtspringwebflux.model.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails

@Document(collection = "user")
data class User(
    @Id
    val id: String?,
    private val username: String,
    private val password: String,
    private val role: String
) : UserDetails {
  override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
      AuthorityUtils.createAuthorityList(role)

  override fun isEnabled(): Boolean = true
  override fun getUsername(): String = this.username
  override fun isCredentialsNonExpired(): Boolean = true
  override fun getPassword(): String = this.password
  override fun isAccountNonExpired(): Boolean = true
  override fun isAccountNonLocked(): Boolean = true
}