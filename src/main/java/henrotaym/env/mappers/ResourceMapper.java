package henrotaym.env.mappers;

import henrotaym.env.entities.Command;
import henrotaym.env.entities.Fight;
import henrotaym.env.entities.Pokemon;
import henrotaym.env.entities.PokemonCatching;
import henrotaym.env.entities.PokemonTrainer;
import henrotaym.env.entities.Trainer;
import henrotaym.env.entities.User;
import henrotaym.env.http.resources.CommandResource;
import henrotaym.env.http.resources.FightResource;
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
  private final FightMapper fightMapper;
  private final CommandMapper commandMapper;

  public PokemonResource pokemonResource(Pokemon pokemon) {
    return this.pokemonMapper.resource(pokemon);
  }

  public UserResource userResource(User user) {
    UserResource userResource = this.userMapper.resource(user);
    if (user.getPokemonsCatchings() != null) {
      List<PokemonCatchingResource> pokemonCatchings =
          user.getPokemonsCatchings().stream()
              .map(pokemonCatching -> this.pokemonCatchingMapper.resource(pokemonCatching))
              .toList();
      userResource.setPokemonsCatchings(pokemonCatchings);
    }
    if (user.getFights() != null) {
      List<FightResource> fights =
          user.getFights().stream().map(fight -> this.fightMapper.resource(fight)).toList();
      userResource.setFights(fights);
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

  public PokemonCatchingResource pokemonCatchingResource(PokemonCatching pokemonCatching) {
    return this.pokemonCatchingMapper.resource(pokemonCatching);
  }

  public PokemonTrainerResource pokemonTrainerResource(PokemonTrainer pokemonTrainer) {
    PokemonTrainerResource pokemonTrainerResource =
        this.pokemonTrainerMapper.resource(pokemonTrainer);
    if (pokemonTrainer.getTrainer() != null) {
      TrainerResource trainerResource = this.trainerMapper.resource(pokemonTrainer.getTrainer());
      pokemonTrainerResource.setTrainer(trainerResource);
    }
    return pokemonTrainerResource;
  }

  public FightResource fightResource(Fight fight) {
    return this.fightMapper.resource(fight);
  }

  public CommandResource commandResource(Command command) {
    return this.commandMapper.resource(command);
  }
}
