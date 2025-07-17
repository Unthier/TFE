package henrotaym.env.services;

import henrotaym.env.entities.PokemonTrainer;
import henrotaym.env.entities.Trainer;
import henrotaym.env.http.requests.PokemonTrainerRequest;
import henrotaym.env.http.resources.PokemonTrainerResource;
import henrotaym.env.mappers.ResourceMapper;
import henrotaym.env.repositories.PokemonTrainerRepository;
import henrotaym.env.repositories.TrainerRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class PokemonTrainerService {
  private PokemonTrainerRepository pokemonTrainerRepository;
  private TrainerRepository trainerRepository;
  private ResourceMapper resourceMapper;

  public PokemonTrainerResource store(PokemonTrainerRequest request) {
    PokemonTrainer pokemonTrainer = new PokemonTrainer();

    return this.storeOrUpdate(request, pokemonTrainer);
  }

  public PokemonTrainerResource update(BigInteger id, PokemonTrainerRequest request) {
    PokemonTrainer pokemonTrainer = this.findById(id);

    return this.storeOrUpdate(request, pokemonTrainer);
  }

  public PokemonTrainerResource show(BigInteger id, Set<String> include) {
    PokemonTrainer pokemonTrainer = this.findById(id);

    return this.resourceMapper.pokemonTrainerResource(pokemonTrainer);
  }

  public List<PokemonTrainerResource> index() {
    return this.pokemonTrainerRepository.findAll().stream()
        .map(pokemonTrainer -> this.resourceMapper.pokemonTrainerResource(pokemonTrainer))
        .toList();
  }

  public void destroy(BigInteger id) {
    PokemonTrainer pokemonTrainer = this.findById(id);

    this.pokemonTrainerRepository.delete(pokemonTrainer);
  }

  private PokemonTrainer findById(BigInteger id) {

    return this.pokemonTrainerRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Pokémon trainer not found."));
  }

  private Trainer getTrainer(PokemonTrainerRequest request) {
    if (request.trainer() == null) {
      return null;
    }

    return this.trainerRepository.findById(request.trainer().id()).get();
  }

  private PokemonTrainerResource storeOrUpdate(
      PokemonTrainerRequest request, PokemonTrainer pokemonTrainer) {
    pokemonTrainer.setTrainer(this.getTrainer(request));
    pokemonTrainer = this.resourceMapper.getPokemonTrainerMapper().request(request, pokemonTrainer);
    pokemonTrainer = this.pokemonTrainerRepository.save(pokemonTrainer);

    return this.resourceMapper.pokemonTrainerResource(pokemonTrainer);
  }
}
