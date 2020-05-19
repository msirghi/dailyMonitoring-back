package com.example.dailymonitoring.configs.utils;

import com.example.dailymonitoring.models.entities.RoleEntity;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@ToString
public class CustomUserDetails implements UserDetails {

  private Collection<? extends GrantedAuthority> authorities;

  private String email;

  private Long id;

  private String password;

  private String username;

  private Boolean enabled;

  private Boolean accountNonExpired;

  private Boolean accountNonLocked;

  private boolean credentialsNonExpired;

//  private RoleEntity role;

  public CustomUserDetails(String email, String username, String password, Boolean enabled,
                           Collection<? extends GrantedAuthority> authorities, Long id) {
    this.email = email;
//    this.role = role;
    this.id = id;
    this.enabled = enabled;
    this.username = username;
    this.password = password;
    this.accountNonExpired = true;
    this.accountNonLocked = true;
    this.credentialsNonExpired = true;
    this.authorities = authorities;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return accountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return accountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return credentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  public void eraseCredentials() {
    this.password = null;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

}
