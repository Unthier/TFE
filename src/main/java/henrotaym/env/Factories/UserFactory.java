package henrotaym.env.Factories;

import henrotaym.env.entities.User;
import henrotaym.env.enums.UserRoleName;
import henrotaym.env.http.requests.AuthRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {
  public User create(AuthRequest request) {
    BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
    User user = new User();

    user.setPassword(encode.encode(request.password()));
    user.setMail(request.mail());
    user.setRole(UserRoleName.USER);
    user.setPokemonsCatchings(null);
    user.setFights(null);
    return user;
  }
}
