package henrotaym.env.services;

import henrotaym.env.Factories.PokemonCatchingFactory;
import henrotaym.env.Factories.PokemonTrainingFactory;
import henrotaym.env.entities.Pokemon;
import henrotaym.env.entities.PokemonCatching;
import henrotaym.env.entities.PokemonTraining;
import henrotaym.env.enums.PokemonCatchingStatusName;
import henrotaym.env.exceptions.TooManyTrainingsException;
import henrotaym.env.http.resources.PokemonCatchingResource;
import henrotaym.env.mappers.ResourceMapper;
import henrotaym.env.repositories.PokemonCatchingRepository;
import henrotaym.env.repositories.PokemonRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
// @Slf4j
public class PokemonCatchingService {
  private PokemonCatchingRepository pokemonCatchingRepository;
  private ResourceMapper resourceMapper;
  private PokemonTrainingFactory pokemonTrainingFactory;
  private PokemonRepository pokemonRepository;
  private PokemonCatchingFactory pokemonCatchingFactory;

  public List<PokemonCatchingResource> index(BigInteger userId) {
    return this.pokemonCatchingRepository.findAll().stream()
        .filter(
            pokemonCatching ->
                pokemonCatching.getUser().getId().equals(userId)
                    && pokemonCatching.getStatus() != PokemonCatchingStatusName.ABANDONED)
        .map(pokemonCatching -> this.resourceMapper.pokemonCatchingResource(pokemonCatching))
        .toList();
  }

  public PokemonCatchingResource show(BigInteger userId, BigInteger pokemonId) {
    PokemonCatching pokemonCatchingResource = this.findById(userId, pokemonId);
    return this.resourceMapper.pokemonCatchingResource(pokemonCatchingResource);
  }

  public PokemonCatchingResource abandon(BigInteger userId, BigInteger pokemonId) {
    PokemonCatching pokemonCatching = this.findById(userId, pokemonId);
    pokemonCatching.setStatus(PokemonCatchingStatusName.ABANDONED);
    this.pokemonCatchingRepository.save(pokemonCatching);
    return this.resourceMapper.pokemonCatchingResource(pokemonCatching);
  }

  public PokemonCatchingResource training(BigInteger userId, BigInteger pokemonId) {
    PokemonCatching pokemonCatching = this.findById(userId, pokemonId);

    if (pokemonCatching.getStatus() == PokemonCatchingStatusName.ABANDONED) {
      throw new EntityNotFoundException("Pokémon catching is abandoned and cannot be trained.");
    }
    if (pokemonCatching.getTrainings().size() >= 5) {
      LocalDateTime now = LocalDateTime.now();
      LocalDateTime fifteenMinutesAgo = now.minusMinutes(15);

      List<PokemonTraining> recentTrainings =
          pokemonCatching.getTrainings().stream()
              .filter(training -> training.getTrainingOn().isAfter(fifteenMinutesAgo))
              .toList();

      if (recentTrainings.size() >= 5) {
        throw new TooManyTrainingsException(
            "You cannot train this Pokémon more than 5 times every 15 minutes.");
      }
    }
    PokemonTraining pokemonTraining = this.pokemonTrainingFactory.create(pokemonCatching);

    if (pokemonCatching.getLevel() + 1 == pokemonCatching.getLevelOfNextEvolution()) {
      pokemonCatching = this.checkEvoledLevel(pokemonCatching);
      // log.info("Pokémon catching has evolved to: {}", pokemonCatching.getName());
    } else {
      pokemonCatching.setAttack(pokemonCatching.getAttack() + 2);
      pokemonCatching.setSpeed(pokemonCatching.getSpeed() + 2);
      pokemonCatching.setLevel(pokemonCatching.getLevel() + 1);
      pokemonCatching.setDefense(pokemonCatching.getDefense() + 2);
      pokemonCatching.setPv(pokemonCatching.getPv() + 2);
    }

    pokemonCatching.getTrainings().add(pokemonTraining);

    this.pokemonCatchingRepository.save(pokemonCatching);
    return this.resourceMapper.pokemonCatchingResource(pokemonCatching);
  }

  private PokemonCatching findById(BigInteger userId, BigInteger pokemonId) {
    return this.pokemonCatchingRepository
        .findById(pokemonId)
        .filter(pokemonCatching -> pokemonCatching.getUser().getId().equals(userId))
        .orElseThrow(() -> new EntityNotFoundException("Pokémon catching not found."));
  }

  private PokemonCatching checkEvoledLevel(PokemonCatching pokemonCatching) {
    Integer level = pokemonCatching.getLevel();
    if ((level + 1) == pokemonCatching.getLevelOfNextEvolution()) {
      Pokemon pokemon =
          this.pokemonRepository.findAll().stream()
              .filter(p -> p.getName().equals(pokemonCatching.getNameOfNextEvolution()))
              .findFirst()
              .orElseThrow(() -> new EntityNotFoundException("Pokemon not found."));
      // log.info(
      //     "Pokémon catching has evolved from {} to {}",
      //     pokemonCatching.getName(),
      //     pokemon.getName());
      PokemonCatching newPokemonCatching =
          this.pokemonCatchingFactory.update(pokemonCatching, pokemon);
      return newPokemonCatching;
    }

    return pokemonCatching;
  }
}
