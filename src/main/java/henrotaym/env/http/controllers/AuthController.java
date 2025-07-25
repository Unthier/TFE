package henrotaym.env.http.controllers;

import henrotaym.env.Factories.UserFactory;
import henrotaym.env.enums.ProfileName;
import henrotaym.env.repositories.UserRepository;
import henrotaym.env.services.AuthSercive;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@Profile(ProfileName.HTTP)
public class AuthController {

  private final UserRepository userRepository;
  private final UserFactory userFactory;
  private final AuthSercive authSercive;

  @GetMapping("/succes")
  public String loging(OAuth2AuthenticationToken token) {
    if (token == null) {
      return "Please log in you.";
    }
    String username = token.getPrincipal().getAttribute("login");
    String tokenString = authSercive.generateToken(username);
    return tokenString;
  }

  // @PostMapping("/signup")
  // public void postMethodName(@RequestBody AuthRequest request) {
  //   User user = this.userFactory.create(request);
  //   this.userRepository.save(user);
  // }

  // @GetMapping("/user")
  // public String getUser() {
  //   return "Welcome, User";
  // }

  // @GetMapping("/admin")
  // public String getAdmin() {
  //   return "Welcome, Admin";
  // }
}
