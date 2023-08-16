package dev.jlcorradi.Arithmetics.web.security;

import dev.jlcorradi.Arithmetics.core.EntityNotFoundException;
import dev.jlcorradi.Arithmetics.core.model.ArithmeticsUser;
import dev.jlcorradi.Arithmetics.core.model.RecordStatus;
import dev.jlcorradi.Arithmetics.core.service.ArithmeticsUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class ArithmeticsUserDetailsService implements UserDetailsService {

  public static final String USER_AUTHORITY = "USER";
  private final ArithmeticsUserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userService.findByUsername(username)
        .map(ArithmeticsUserDetailsService::mapToUserDetails)
        .orElseThrow(EntityNotFoundException::new);
  }

  private static UserDetails mapToUserDetails(ArithmeticsUser arithmeticsUser) {
    return new UserDetails() {
      @Override
      public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(USER_AUTHORITY);
      }

      @Override
      public String getPassword() {
        return arithmeticsUser.getPassword();
      }

      @Override
      public String getUsername() {
        return arithmeticsUser.getEmail();
      }

      @Override
      public boolean isAccountNonExpired() {
        return RecordStatus.ACTIVE == arithmeticsUser.getStatus();
      }

      @Override
      public boolean isAccountNonLocked() {
        return RecordStatus.ACTIVE == arithmeticsUser.getStatus();
      }

      @Override
      public boolean isCredentialsNonExpired() {
        return RecordStatus.ACTIVE == arithmeticsUser.getStatus();
      }

      @Override
      public boolean isEnabled() {
        return RecordStatus.ACTIVE == arithmeticsUser.getStatus();
      }
    };
  }

}
