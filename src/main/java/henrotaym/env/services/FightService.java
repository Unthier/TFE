package henrotaym.env.services;

import henrotaym.env.Factories.PokemonCatchingFactory;
import henrotaym.env.entities.Fight;
import henrotaym.env.entities.PokemonCatching;
import henrotaym.env.entities.PokemonTrainer;
import henrotaym.env.entities.Trainer;
import henrotaym.env.entities.User;
import henrotaym.env.enums.FightStateName;
import henrotaym.env.enums.PokemonCatchingStatusName;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class FightService {
  private final FightRepository fightRepository;
  private final ResourceMapper resourceMapper;
  private final UserRepository userRepository;
  private final PokemonCatchingRepository pokemonCatchingRepository;
  private final TrainerRepository trainerRepository;
  private final PokemonTrainerRepository pokemonTrainerRepository;
  private final PokemonCatchingFactory pokemonCatchingFactory;

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
            .filter(pokemon -> pokemon.getStatus() != PokemonCatchingStatusName.ABANDONED)
            .orElseThrow(() -> new EntityNotFoundException("Pokemon catching not found."));
    pokemonCatching.setStatus(PokemonCatchingStatusName.FIGHT);
    PokemonTrainer pokemonTrainer = trainer.getPokemons().get(0);

    if (pokemonTrainer == null) {
      throw new EntityNotFoundException("Trainer has no Pokemon.");
    }
    fight = this.startTour(pokemonCatching, pokemonTrainer);
    fight.setState(this.getFightState(fight, pokemonCatching, pokemonTrainer));
    fight.setUser(user);
    fight.setPokemonCatchingId(pokemonCatching.getId());
    fight.setPokemonTrainerId(pokemonTrainer.getId());
    fight.setTours(1);
    this.fightRepository.save(fight);

    if (fight.getState() == FightStateName.IN_PROGRESS) {
      Integer pokemonCatchingPv = pokemonCatching.getPv() - fight.getPokemonCatchingDamage();
      Integer pokemonTrainerPv = pokemonTrainer.getPv() - fight.getPokemonTrainerDamage();
      return "Fight started. Your pokémon has "
          + pokemonCatchingPv
          + " PV and the trainer's pokémon has "
          + pokemonTrainerPv
          + " PV.";
    }
    pokemonCatching.setStatus(PokemonCatchingStatusName.NEUTRE);
    this.pokemonCatchingRepository.save(pokemonCatching);

    if (fight.getState() == FightStateName.WINED) {
      return "You win in the firts tours! Congratulation .";
    }
    Integer random = (int) (Math.random() * 2);
    if (random == 0) {
      return this.getReward(user);
    }
    return "You lost in the first tours. Training yours Pokémons and another day you win.";
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
      fight.setPokemonTrainerDamage(trainerDamage);
    } else {
      Integer userDamage = pokemonTrainer.getAttack() - pokemonCatching.getDefense();
      if (userDamage <= 0) {
        userDamage = 1;
      }
      fight.setPokemonCatchingDamage(userDamage);
    }

    if (isCatchingStarted) {
      if (pokemonTrainer.getPv() > pokemonTrainer.getDamage()) {
        Integer userDamage = pokemonTrainer.getAttack() - pokemonCatching.getDefense();
        if (userDamage <= 0) {
          userDamage = 1;
        }
        fight.setPokemonCatchingDamage(userDamage);
      }

    } else {
      if (pokemonCatching.getPv() > pokemonCatching.getDamage()) {
        Integer trainerDamage = pokemonCatching.getAttack() - pokemonTrainer.getDefense();
        if (trainerDamage <= 0) {
          trainerDamage = 1;
        }
        fight.setPokemonTrainerDamage(trainerDamage);
      }
    }
    return fight;
  }

  private FightStateName getFightState(
      Fight fight, PokemonCatching pokemonCatching, PokemonTrainer pokemonTrainer) {
    if (pokemonCatching.getPv() > fight.getPokemonCatchingDamage()
        && pokemonTrainer.getPv() < fight.getPokemonTrainerDamage()) {
      return FightStateName.WINED;
    } else if (pokemonCatching.getPv() < fight.getPokemonCatchingDamage()
        && pokemonTrainer.getPv() > fight.getPokemonTrainerDamage()) {
      return FightStateName.LOSED;
    }
    return FightStateName.IN_PROGRESS;
  }

  public String fightContinue(BigInteger userId) {
    User user =
        this.userRepository
            .findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found."));
    Fight fight =
        this.findById(
            userId,
            user.getFights().stream()
                .filter(f -> f.getState().equals(FightStateName.IN_PROGRESS))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("No fight in progress."))
                .getId());
    PokemonCatching pokemonCatching =
        this.pokemonCatchingRepository
            .findById(fight.getPokemonCatchingId())
            .orElseThrow(() -> new EntityNotFoundException("Pokemon catching not found."));
    PokemonTrainer pokemonTrainer =
        this.pokemonTrainerRepository
            .findById(fight.getPokemonTrainerId())
            .orElseThrow(() -> new EntityNotFoundException("Pokemon trainer not found."));

    fight = this.fightNextTour(fight, pokemonCatching, pokemonTrainer);
    fight.setTours(fight.getTours() + 1);
    this.fightRepository.save(fight);
    if (fight.getState() == FightStateName.IN_PROGRESS) {
      Integer pokemonCatchingPv = pokemonCatching.getPv() - fight.getPokemonCatchingDamage();
      Integer pokemonTrainerPv = pokemonTrainer.getPv() - fight.getPokemonTrainerDamage();
      return "Fight in progress. Your pokémon has "
          + pokemonCatchingPv
          + " PV and the trainer's pokémon has "
          + pokemonTrainerPv
          + " PV.";
    }
    pokemonCatching.setStatus(PokemonCatchingStatusName.NEUTRE);
    this.pokemonCatchingRepository.save(pokemonCatching);
    if (fight.getState() == FightStateName.LOSED) {
      return "You lost. Training yours Pokémons and another day you win.";
    }

    Integer random = (int) (Math.random() * 2);
    if (random == 0) {
      return this.getReward(user);
    }
    return "You win! Congratulation .";
  }

  private Fight fightNextTour(
      Fight fight, PokemonCatching pokemonCatching, PokemonTrainer pokemonTrainer) {

    Boolean isCatchingStarted = false;
    if (pokemonCatching.getSpeed() > pokemonTrainer.getSpeed()) {
      isCatchingStarted = true;
      Integer trainerDamage = pokemonCatching.getAttack() - pokemonTrainer.getDefense();
      if (trainerDamage <= 0) {
        trainerDamage = 1;
      }
      fight.setPokemonTrainerDamage(trainerDamage + fight.getPokemonTrainerDamage());
    } else {
      Integer userDamage = pokemonTrainer.getAttack() - pokemonCatching.getDefense();
      if (userDamage <= 0) {
        userDamage = 1;
      }
      fight.setPokemonCatchingDamage(userDamage + fight.getPokemonCatchingDamage());
    }

    if (isCatchingStarted) {
      if (pokemonTrainer.getPv() > pokemonTrainer.getDamage()) {
        Integer userDamage = pokemonTrainer.getAttack() - pokemonCatching.getDefense();
        if (userDamage <= 0) {
          userDamage = 1;
        }
        fight.setPokemonCatchingDamage(userDamage + fight.getPokemonCatchingDamage());
      }

    } else {
      if (pokemonCatching.getPv() > pokemonCatching.getDamage()) {
        Integer trainerDamage = pokemonCatching.getAttack() - pokemonTrainer.getDefense();
        if (trainerDamage <= 0) {
          trainerDamage = 1;
        }
        fight.setPokemonTrainerDamage(trainerDamage + fight.getPokemonTrainerDamage());
      }
    }
    fight.setState(this.getFightState(fight, pokemonCatching, pokemonTrainer));
    return fight;
  }

  private String getReward(User user) {

    PokemonCatching pokemonCatching = this.pokemonCatchingFactory.createEgg();
    pokemonCatching.setUser(user);
    user.getPokemonsCatchings().add(pokemonCatching);
    this.userRepository.save(user);
    return "You win! and you get en egg as reward. This egg will hatch in 6 houres.";
  }
}
