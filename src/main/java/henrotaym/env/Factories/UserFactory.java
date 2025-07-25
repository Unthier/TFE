package henrotaym.env.Factories;

import henrotaym.env.entities.User;
import henrotaym.env.enums.UserRoleName;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {
  public User create(String username) {
    BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
    User user = new User();
    user.setName(username);
    user.setPassword(null);
    user.setMail(null);
    user.setRole(UserRoleName.USER);
    user.setPokemonsCatchings(null);
    user.setFights(null);
    return user;
  }
}
