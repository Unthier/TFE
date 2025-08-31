package henrotaym.env.http.controllers;

import henrotaym.env.Factories.UserFactory;
import henrotaym.env.entities.User;
import henrotaym.env.enums.ProfileName;
import henrotaym.env.http.requests.UserRequest;
import henrotaym.env.repositories.UserRepository;
import henrotaym.env.services.AuthSercive;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@Profile(ProfileName.HTTP)
@RequestMapping("auth")
public class AuthController {

  private final UserRepository userRepository;
  private final UserFactory userFactory;
  private final AuthSercive authSercive;

  @GetMapping("/token")
  public String loging(OAuth2AuthenticationToken token) {
    if (token == null) {
      return "Please log in you.";
    }
    String username = token.getPrincipal().getAttribute("login");
    String tokenString = authSercive.generateToken(username);
    return tokenString;
  }

  @PostMapping("/signup")
  public String signup(@RequestBody @Valid UserRequest request) {
    User user = this.userFactory.create(request);
    this.userRepository.save(user);

    return "User created successfully";
  }

  // @GetMapping("/user")
  // public String getUser() {
  //   return "Welcome, User";
  // }

  // @GetMapping("/admin")
  // public String getAdmin() {
  //   return "Welcome, Admin";
  // }
}
