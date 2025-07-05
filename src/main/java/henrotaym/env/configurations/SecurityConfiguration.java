package henrotaym.env.configurations;

import org.springframework.context.annotation.Configuration;

@Configuration
// @EnableWebSecurity
public class SecurityConfiguration {

  // @Bean
  // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
  //   http.csrf(csrf -> csrf.disable())
  //       .authorizeHttpRequests(
  //           auth ->
  //               auth.requestMatchers(HttpMethod.POST, "/users/*")
  //                   .permitAll()
  //                   .requestMatchers(HttpMethod.GET, "/users/*")
  //                   .permitAll()
  //                   .anyRequest()
  //                   .authenticated())
  //       .httpBasic(Customizer.withDefaults());

  //   return http.build();
  // }
}
