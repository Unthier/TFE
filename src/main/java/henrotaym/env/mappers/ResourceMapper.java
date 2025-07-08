package henrotaym.env.mappers;

import henrotaym.env.entities.Pokemon;
import henrotaym.env.entities.Trainer;
import henrotaym.env.entities.User;
import henrotaym.env.http.resources.PokemonCatchingResource;
import henrotaym.env.http.resources.PokemonResource;
import henrotaym.env.http.resources.PokemonTrainerResource;
import henrotaym.env.http.resources.TrainerResource;
import henrotaym.env.http.resources.UserResource;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class ResourceMapper {
  private final PokemonMapper pokemonMapper;
  private final UserMapper userMapper;
  private final TrainerMapper trainerMapper;
  private final PokemonCatchingMapper pokemonCatchingMapper;
  private final PokemonTrainerMapper pokemonTrainerMapper;

  public PokemonResource pokemonResource(Pokemon pokemon) {
    return this.pokemonMapper.resource(pokemon);
  }

  public UserResource userResource(User user) {
    UserResource userResource = this.userMapper.resource(user);
    if (user.getPokemonsCatching() != null) {
      List<PokemonCatchingResource> pokemonCatchings =
          user.getPokemonsCatching().stream()
              .map(pokemonCatching -> this.pokemonCatchingMapper.resource(pokemonCatching))
              .toList();
      userResource.setPokemonsCatching(pokemonCatchings);
    }
    return userResource;
  }

  public TrainerResource trainerResource(Trainer trainer) {
    TrainerResource trainerResource = this.trainerMapper.resource(trainer);
    if (trainer.getPokemons() != null) {
      List<PokemonTrainerResource> pokemonCatchings =
          trainer.getPokemons().stream()
              .map(pokemonTrainer -> this.pokemonTrainerMapper.resource(pokemonTrainer))
              .toList();
      trainerResource.setPokemons(pokemonCatchings);
    }

    return trainerResource;
  }
}
