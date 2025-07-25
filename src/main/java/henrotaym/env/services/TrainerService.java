package henrotaym.env.services;

import henrotaym.env.entities.PokemonTrainer;
import henrotaym.env.entities.Trainer;
import henrotaym.env.http.requests.TrainerRequest;
import henrotaym.env.http.resources.TrainerResource;
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
public class TrainerService {
  private TrainerRepository trainerRepository;
  private ResourceMapper resourceMapper;
  private PokemonTrainerRepository pokemonTrainerRepository;

  public TrainerResource store(TrainerRequest request) {
    Trainer trainer = new Trainer();

    return this.storeOrUpdate(request, trainer);
  }

  public TrainerResource update(BigInteger id, TrainerRequest request) {
    Trainer trainer = this.findById(id);

    return this.storeOrUpdate(request, trainer);
  }

  public TrainerResource show(BigInteger id, Set<String> include) {
    Trainer trainer = this.findById(id);

    return this.resourceMapper.trainerResource(trainer);
  }

  public List<TrainerResource> index() {
    return this.trainerRepository.findAll().stream()
        .map(trainer -> this.resourceMapper.trainerResource(trainer))
        .toList();
  }

  public void destroy(BigInteger id) {
    Trainer trainer = this.findById(id);

    this.trainerRepository.delete(trainer);
  }

  private Trainer findById(BigInteger id) {
    return this.trainerRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Trainer not found."));
  }

  private List<PokemonTrainer> getPokemonTrainers(TrainerRequest request) {
    if (request.pokemonsTrainer() == null) return null;
    return request.pokemonsTrainer().stream()
        .map(
            pokemontrainer ->
                this.pokemonTrainerRepository
                    .findById(pokemontrainer.id())
                    .orElseThrow(() -> new EntityNotFoundException("Trainer pokemon not found.")))
        .toList();
  }

  private TrainerResource storeOrUpdate(TrainerRequest request, Trainer trainer) {

    trainer.setPokemons(this.getPokemonTrainers(request));
    trainer = this.resourceMapper.getTrainerMapper().request(request, trainer);
    trainer = this.trainerRepository.save(trainer);

    return this.resourceMapper.trainerResource(trainer);
  }
}
