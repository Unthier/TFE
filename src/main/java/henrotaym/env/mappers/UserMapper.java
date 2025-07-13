package henrotaym.env.mappers;

import org.springframework.stereotype.Component;

// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import henrotaym.env.entities.User;
import henrotaym.env.http.requests.UserRequest;
import henrotaym.env.http.requests.relationships.UserRelationshipRequest;
import henrotaym.env.http.resources.UserResource;

@Component
public class UserMapper {
  public UserResource resource(User user) {
    return new UserResource(user.getId(), user.getName(), user.getRole(),user.getMail());
  }

  public User request(UserRequest request, User user) {
    // BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    user.setName(request.name());
    user.setMail(request.mail());
    user.setRole(request.role());
    //user.setCaptchingNumber(request.catchingNumber());
    user.setPassword(request.password());

    return user;
  }

  public UserRelationshipRequest relationshipRequest(User user) {
    return new UserRelationshipRequest(user.getId());
  }
}
