package henrotaym.env.mappers;

import henrotaym.env.entities.User;
import henrotaym.env.http.requests.UserRequest;
import henrotaym.env.http.resources.UserResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
  public UserResource resource(User user) {
    return new UserResource(user.getId(), user.getName(), user.getCaptchingNumber());
  }

  public User request(UserRequest request, User user) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    user.setName(request.name());
    user.setCaptchingNumber(request.catchingNumber());
    user.setPassword(encoder.encode(request.password()));

    return user;
  }
}
