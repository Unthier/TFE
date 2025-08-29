package henrotaym.env.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

  //    @Bean
  //   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
  //     return http
  //         // 1) CSRF: soit tu le désactives globalement, soit tu ignores juste /users/catch
  //         .csrf(csrf -> csrf
  //             .ignoringRequestMatchers("/users") // option ciblée
  //             // .disable() // option globale (plus simple pour Postman, moins sécure)
  //         )
  //         .authorizeHttpRequests(auth -> auth
  //             // autorise les endpoints publics / techniques d’OAuth2
  //             .requestMatchers("/succes").permitAll()
  //             .requestMatchers("/login**", "/oauth2/**").permitAll()
  //             // tes endpoints publics existants
  //             .requestMatchers("/pokemons", "/pokemons/**").permitAll()

  //             // >>> très important pour Postman + ton contrôle manuel du Bearer
  //             .requestMatchers(HttpMethod.POST, "/users/catch").permitAll()

  //             // tout le reste requiert une session OAuth2 valide
  //             .anyRequest().authenticated()
  //         )
  //         // 2) Empêcher les redirections HTML → renvoyer 401 JSON plutôt que HTML
  //         .exceptionHandling(ex -> ex
  //             .authenticationEntryPoint((req, res, e) -> {
  //               res.setStatus(401);
  //               res.setContentType("application/json");
  //               res.getWriter().write("{\"error\":\"Unauthorized\"}");
  //             })
  //         )
  //         // 3) Login OAuth2 (github) → après succès, tu renvoies ton token
  //         .oauth2Login(oauth2 -> oauth2
  //             .defaultSuccessUrl("/succes", true)
  //         )
  //         .logout(logout -> logout
  //             .logoutUrl("/logout")
  //             .logoutSuccessUrl("/login")
  //             .invalidateHttpSession(true)
  //             .deleteCookies("JSESSIONID")
  //         )
  //         .build();
  //   }
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(
            auth -> {
              // auth.anyRequest().permitAll();
              auth.requestMatchers("/pokemons").permitAll();
              auth.requestMatchers("/pokemons/**").permitAll();
              auth.requestMatchers("/commands").permitAll();
              auth.anyRequest().authenticated();
            })
        .logout(
            logout ->
                logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID"))
        .oauth2Login(oauth2 -> oauth2.defaultSuccessUrl("/succes"))
        .build();
  }
}
