package henrotaym.env.services;

import henrotaym.env.entities.Fight;
import henrotaym.env.entities.PokemonCatching;
import henrotaym.env.entities.PokemonTrainer;
import henrotaym.env.entities.Trainer;
import henrotaym.env.entities.User;
import henrotaym.env.enums.FightStateName;
import henrotaym.env.exceptions.UserAlreadyInFightException;
import henrotaym.env.http.resources.FightResource;
import henrotaym.env.mappers.ResourceMapper;
import henrotaym.env.repositories.FightRepository;
import henrotaym.env.repositories.PokemonCatchingRepository;
import henrotaym.env.repositories.PokemonTrainerRepository;
import henrotaym.env.repositories.TrainerRepository;
import henrotaym.env.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigInteger;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FightService {
  private final FightRepository fightRepository;
  private final ResourceMapper resourceMapper;
  private final UserRepository userRepository;
  private final PokemonCatchingRepository pokemonCatchingRepository;
  private final TrainerRepository trainerRepository;
  private final PokemonTrainerRepository pokemonTrainerRepository;

  public List<FightResource> index(BigInteger userId) {
    return this.fightRepository.findAll().stream()
        .filter(fight -> fight.getUser().getId().equals(userId))
        .map(fight -> this.resourceMapper.fightResource(fight))
        .toList();
  }

  public FightResource show(BigInteger userId, BigInteger fightId) {
    Fight fightResource = this.findById(userId, fightId);
    return this.resourceMapper.fightResource(fightResource);
  }

  private Fight findById(BigInteger userId, BigInteger fightId) {
    return this.fightRepository
        .findById(fightId)
        .filter(fight -> fight.getUser().getId().equals(userId))
        .orElseThrow(() -> new EntityNotFoundException("Fight catching not found."));
  }

  public String fightAbandoned(BigInteger userId, BigInteger fightId) {
    Fight fight = this.findById(userId, fightId);
    PokemonCatching pokemonCatching =
        this.pokemonCatchingRepository
            .findById(fight.getPokemonCatchingId())
            .orElseThrow(() -> new EntityNotFoundException("Pokemon catching not found."));
    PokemonTrainer pokemonTrainer =
        this.pokemonTrainerRepository
            .findById(fight.getPokemonTrainerId())
            .orElseThrow(() -> new EntityNotFoundException("Pokemon trainer not found."));
    fight.setState(FightStateName.ABANDONED);
    pokemonCatching.setDamage(0);
    pokemonTrainer.setDamage(0);
    this.fightRepository.save(fight);
    return "You have abandoned the fight.";
  }

  public String store(
      BigInteger userId, BigInteger pokemonCatchinBigInteger, BigInteger trainerId) {
    Fight fight = new Fight();
    User user =
        this.userRepository
            .findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found."));
    if (user.getFights().stream().anyMatch(f -> f.getState().equals(FightStateName.IN_PROGRESS))) {
      throw new UserAlreadyInFightException("You are already in fight.");
    }
    Trainer trainer =
        this.trainerRepository
            .findById(trainerId)
            .orElseThrow(() -> new EntityNotFoundException("Trainer not found."));
    PokemonCatching pokemonCatching =
        this.pokemonCatchingRepository
            .findById(pokemonCatchinBigInteger)
            .orElseThrow(() -> new EntityNotFoundException("Pokemon catching not found."));
    PokemonTrainer pokemonTrainer = trainer.getPokemons().get(0);
    if (pokemonTrainer == null) {
      throw new EntityNotFoundException("Trainer has no Pokemon.");
    }
    fight = this.startTour(pokemonCatching, pokemonTrainer);
    fight.setUser(user);
    fight.setPokemonCatchingId(pokemonCatching.getId());
    fight.setPokemonTrainerId(trainer.getId());
    fight.setTours(1);
    fight.setState(FightStateName.IN_PROGRESS);
    this.fightRepository.save(fight);

    return "Fight created successfully.";
  }

  private Fight startTour(PokemonCatching pokemonCatching, PokemonTrainer pokemonTrainer) {
    Fight fight = new Fight();

    Boolean isCatchingStarted = false;
    if (pokemonCatching.getSpeed() > pokemonTrainer.getSpeed()) {
      isCatchingStarted = true;
      Integer trainerDamage = pokemonCatching.getAttack() - pokemonTrainer.getDefense();
      if (trainerDamage <= 0) {
        trainerDamage = 1;
      }
      pokemonTrainer.setDamage(trainerDamage);
      fight.setPokemonTrainerDamage(trainerDamage);
    } else {
      Integer userDamage = pokemonTrainer.getAttack() - pokemonCatching.getDefense();
      if (userDamage <= 0) {
        userDamage = 1;
      }
      pokemonCatching.setDamage(userDamage);
      fight.setPokemonCatchingDamage(userDamage);
    }

    if (isCatchingStarted) {
      if (pokemonTrainer.getPv() > pokemonTrainer.getDamage()) {
        Integer userDamage = pokemonTrainer.getAttack() - pokemonCatching.getDefense();
        if (userDamage <= 0) {
          userDamage = 1;
        }
        pokemonCatching.setDamage(userDamage);
        fight.setPokemonCatchingDamage(userDamage);
      }

    } else {
      if (pokemonCatching.getPv() > pokemonCatching.getDamage()) {
        Integer trainerDamage = pokemonCatching.getAttack() - pokemonTrainer.getDefense();
        if (trainerDamage <= 0) {
          trainerDamage = 1;
        }
        pokemonTrainer.setDamage(trainerDamage);
        fight.setPokemonTrainerDamage(trainerDamage);
      }
    }

    return fight;
  }
}
