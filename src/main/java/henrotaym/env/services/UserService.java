package henrotaym.env.services;

import henrotaym.env.Factories.PokemonCatchingFactory;
import henrotaym.env.entities.Pokemon;
import henrotaym.env.entities.PokemonCatching;
import henrotaym.env.entities.User;
import henrotaym.env.exceptions.MaxNumberCatchingException;
import henrotaym.env.http.requests.UserRequest;
import henrotaym.env.http.resources.UserResource;
import henrotaym.env.mappers.ResourceMapper;
import henrotaym.env.repositories.PokemonCatchingRepository;
import henrotaym.env.repositories.PokemonRepository;
import henrotaym.env.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
  private UserRepository userRepository;
  private ResourceMapper resourceMapper;
  private PokemonCatchingRepository pokemonCatchingRepository;
  private PokemonCatchingFactory pokemonCatchingFactory;
  private PokemonRepository pokemonRepository;

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

  private List<PokemonCatching> getPokemonCatching(UserRequest request) {
    if (request.pokemonsCatching() == null) return null;
    return request.pokemonsCatching().stream()
        .map(pokemonCatching -> this.pokemonCatchingRepository.findById(pokemonCatching.id()).get())
        .toList();
  }

  private UserResource storeOrUpdate(UserRequest request, User user) {
    user.setPokemonsCatchings(this.getPokemonCatching(request));
    user = this.resourceMapper.getUserMapper().request(request, user);
    user = this.userRepository.save(user);

    return this.resourceMapper.userResource(user);
  }

   
}
