package henrotaym.env.services;

import henrotaym.env.entities.User;
import henrotaym.env.http.requests.UserRequest;
import henrotaym.env.http.resources.UserResource;
import henrotaym.env.mappers.ResourceMapper;
import henrotaym.env.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
  private UserRepository userRepository;
  private ResourceMapper resourceMapper;

  public UserResource store(UserRequest request) {
    User user = new User();

    return this.storeOrUpdate(request, user);
  }

  public UserResource update(BigInteger id, UserRequest request) {
    User user = this.findById(id);

    return this.storeOrUpdate(request, user);
  }

  public UserResource show(BigInteger id, Set<String> include) {
    User user = this.findById(id);

    return this.resourceMapper.userResource(user);
  }

  public List<UserResource> index() {
    return this.userRepository.findAll().stream()
        .map(user -> this.resourceMapper.userResource(user))
        .toList();
  }

  public void destroy(BigInteger id) {
    User user = this.findById(id);

    this.userRepository.delete(user);
  }

  private User findById(BigInteger id) {
    return this.userRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("User not found."));
  }

  private UserResource storeOrUpdate(UserRequest request, User user) {
    user = this.resourceMapper.getUserMapper().request(request, user);
    user = this.userRepository.save(user);

    return this.resourceMapper.userResource(user);
  }
}
