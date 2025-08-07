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
              // auth.anyRequest().permitAll();
              auth.requestMatchers("/pokemons").permitAll();
              auth.requestMatchers("/pokemons/**").permitAll();
              auth.anyRequest().authenticated();
            })
        .logout(
            logout ->
                logout
                    .logoutUrl("/logout") 
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
            )
        .oauth2Login(
            oauth2 -> oauth2.defaultSuccessUrl("/succes")
            )
        // .formLogin(
        //     form ->
        //         form.defaultSuccessUrl("/pokemons")
        // formulaire
        //     )
        .build();
  }
}
