package henrotaym.env.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.authorizeHttpRequests(
            auth -> {
              // auth.anyRequest().authenticated();
              auth.requestMatchers("/api/user").permitAll();
              auth.anyRequest().authenticated();
            })
        .logout(
            logout ->
                logout
                    .logoutUrl("/logout") // URL qui déclenche le logout
                    .logoutSuccessUrl("/login") // Où rediriger après déconnexion
                    .invalidateHttpSession(true) // Invalide la session
                    .deleteCookies("JSESSIONID") // Supprime le cookie de session
            )
        .oauth2Login(
            oauth2 -> oauth2.defaultSuccessUrl("/succes") // Redirection après connexion réussie
            )
        // .formLogin(
        //     form ->
        //         form.defaultSuccessUrl("/pokemons", true) // Redirection aussi pour login
        // formulaire
        //     )
        .build();
  }

  // @Bean
  // public UserDetailsService users() {
  //   UserDetails guest =
  //       User.builder()
  //           .username("guest")
  //           .password(passwordEncoder().encode("guest"))
  //           .roles(UserRoleName.GUEST.toString())
  //           .build();
  //   UserDetails user =
  //       User.builder()
  //           .username("user")
  //           .password(passwordEncoder().encode("user"))
  //           .roles(UserRoleName.USER.toString(), UserRoleName.GUEST.toString())
  //           .build();
  //   UserDetails admin =
  //       User.builder()
  //           .username("admin")
  //           .password(passwordEncoder().encode("admin"))
  //           .roles(
  //               UserRoleName.ADMIN.toString(),
  //               UserRoleName.USER.toString(),
  //               UserRoleName.GUEST.toString())
  //           .build();
  //   return new InMemoryUserDetailsManager(guest, user, admin);
  // }

  // @Bean
  // public BCryptPasswordEncoder passwordEncoder() {
  //   return new BCryptPasswordEncoder();
  // }
}
