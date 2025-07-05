package henrotaym.env.mappers;

import henrotaym.env.entities.Pokemon;
import henrotaym.env.entities.User;
import henrotaym.env.http.resources.PokemonResource;
import henrotaym.env.http.resources.UserResource;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class ResourceMapper {
  private final PokemonMapper pokemonMapper;
  private final UserMapper userMapper;

  public PokemonResource pokemonResource(Pokemon pokemon) {
    return this.pokemonMapper.resource(pokemon);
  }

  public UserResource userResource(User user) {
    return this.userMapper.resource(user);
  }
}
