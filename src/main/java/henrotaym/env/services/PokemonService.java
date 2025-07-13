package henrotaym.env.services;

import henrotaym.env.entities.Pokemon;
import henrotaym.env.http.requests.PokemonRequest;
import henrotaym.env.http.resources.PokemonResource;
import henrotaym.env.mappers.ResourceMapper;
import henrotaym.env.repositories.PokemonRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PokemonService {
  private PokemonRepository pokemonRepository;
  private ResourceMapper resourceMapper;

  public PokemonResource store(PokemonRequest request) {
    Pokemon pokemon = new Pokemon();

    return this.storeOrUpdate(request, pokemon);
  }

  public PokemonResource update(BigInteger id, PokemonRequest request) {
    Pokemon pokemon = this.findById(id);

    return this.storeOrUpdate(request, pokemon);
  }

  public PokemonResource show(BigInteger id, Set<String> include) {
    Pokemon pokemon = this.findById(id);

    return this.resourceMapper.pokemonResource(pokemon);
  }

  public List<PokemonResource> index() {
    return this.pokemonRepository.findAll().stream()
        .map(pokemon -> this.resourceMapper.pokemonResource(pokemon))
        .toList();
  }

  public void destroy(BigInteger id) {
    Pokemon pokemon = this.findById(id);

    this.pokemonRepository.delete(pokemon);
  }

  private Pokemon findById(BigInteger id) {
    return this.pokemonRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Pokémon not found."));
  }

  private PokemonResource storeOrUpdate(PokemonRequest request, Pokemon pokemon) {
    pokemon = this.resourceMapper.getPokemonMapper().request(request, pokemon);
    pokemon = this.pokemonRepository.save(pokemon);

    return this.resourceMapper.pokemonResource(pokemon);
  }
}
