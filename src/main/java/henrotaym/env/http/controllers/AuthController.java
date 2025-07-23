package henrotaym.env.http.controllers;

import henrotaym.env.Factories.UserFactory;
import henrotaym.env.entities.User;
import henrotaym.env.enums.ProfileName;
import henrotaym.env.http.requests.AuthRequest;
import henrotaym.env.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@Profile(ProfileName.HTTP)
public class AuthController {

  private final UserRepository userRepository;
  private final UserFactory userFactory;

  // private final OAuth2AuthorizedClientService authorizedClientService;

  // @PostMapping("/login")
  // public ResponseEntity<AuthResource> loging(@RequestBody @Valid AuthRequest request) {

  //   return this.authService.loging(request);
  // }

  @GetMapping("/succes")
  public String loging(OAuth2AuthenticationToken token) {
    if (token == null) {
      return "Please log in you.";
    }
    // name = (token.getPrincipal().getAttribute("name") != null ?
    // token.getPrincipal().getAttribute("name") : token.getPrincipal().getAttribute("login"));
    // email =
    return "Hello, " + token.getPrincipal().getAttribute("email");
  }

  // @GetMapping("/succes")
  // public String loging(Principal token) {
  //   return getOauth2LoginInfo(token).toString();
  // }

  // private StringBuffer getOauth2LoginInfo(Principal user) {
  //   StringBuffer protectedInfo = new StringBuffer();

  //   OAuth2AuthenticationToken authToken = ((OAuth2AuthenticationToken) user);
  //   OAuth2AuthorizedClient authClient =
  //       this.authorizedClientService.loadAuthorizedClient(
  //           authToken.getAuthorizedClientRegistrationId(), authToken.getName());
  //   if (authToken.isAuthenticated()) {

  //     Map<String, Object> userAttributes =
  //         ((DefaultOAuth2User) authToken.getPrincipal()).getAttributes();

  //     String userToken = authClient.getAccessToken().getTokenValue();
  //     protectedInfo.append("Welcome, " + userAttributes.get("name") + "<br><br>");
  //     protectedInfo.append("e-mail: " + userAttributes.get("email") + "<br><br>");
  //     protectedInfo.append("Access Token: " + userToken + "<br><br>");
  //   } else {
  //     protectedInfo.append("NA");
  //   }
  //   return protectedInfo;
  // }

  // public String getUserInfo(Principal user) {
  //   StringBuffer userInfo = new StringBuffer();
  //   return userInfo.toString();
  // }

  // private StringBuffer getUsernamePasswordLoginInfo(Principal user) {
  //   StringBuffer usernameInfo = new StringBuffer();

  //   UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken) user);
  //   if (token.isAuthenticated()) {
  //     org.springframework.security.core.userdetails.User u =
  //         (org.springframework.security.core.userdetails.User) token.getPrincipal();
  //     usernameInfo.append("Welcome, " + u.getUsername());
  //   } else {
  //     usernameInfo.append("NA");
  //   }
  //   return usernameInfo;
  // }

  // @PostMapping("/signup")
  // public String singup(@RequestBody @Valid AuthRequest request) {

  //   return "User signed up successfully!";
  // }

  // @PostMapping("/logout")
  // public void logout() {}

  @PostMapping("/signup")
  public void postMethodName(@RequestBody AuthRequest request) {
    User user = this.userFactory.create(request);
    this.userRepository.save(user);
  }

  @GetMapping("/user")
  public String getUser() {
    return "Welcome, User";
  }

  @GetMapping("/admin")
  public String getAdmin() {
    return "Welcome, Admin";
  }

  //   @GetMapping("/succes")
  // public String login(OAuth2AuthenticationToken token) {
  //     return getOauth2LoginInfo(token).toString();
  // }

  // private StringBuffer getOauth2LoginInfo(OAuth2AuthenticationToken authToken) {
  //     StringBuffer protectedInfo = new StringBuffer();

  //     OAuth2AuthorizedClient authClient = authorizedClientService
  //             .loadAuthorizedClient(authToken.getAuthorizedClientRegistrationId(),
  // authToken.getName());

  //     if (authToken.isAuthenticated()) {
  //         Map<String, Object> userAttributes = authToken.getPrincipal().getAttributes();
  //         String userToken = authClient.getAccessToken().getTokenValue();

  //         protectedInfo.append("Welcome, " + (userAttributes.get("name") != null
  //                 ? userAttributes.get("name")
  //                 : userAttributes.get("login")) + "<br><br>");
  //         protectedInfo.append("Email: " + userAttributes.get("email") + "<br><br>");
  //         protectedInfo.append("Access Token: " + userToken + "<br><br>");
  //     } else {
  //         protectedInfo.append("Non authentifié");
  //     }

  //     return protectedInfo;
  // }
}
