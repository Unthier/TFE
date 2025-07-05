package henrotaym.env.http.controllers;

import henrotaym.env.enums.ProfileName;
import henrotaym.env.http.requests.PokemonRequest;
import henrotaym.env.http.resources.PokemonResource;
import henrotaym.env.services.PokemonService;
import jakarta.validation.Valid;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("pokemons")
@Profile(ProfileName.HTTP)
public class PokemonController {
  private final PokemonService pokemonService;

  @PostMapping("")
  public ResponseEntity<PokemonResource> store(@RequestBody @Valid PokemonRequest request) {
    PokemonResource pokemon = this.pokemonService.store(request);

    return ResponseEntity.status(HttpStatus.CREATED).body(pokemon);
  }

  @GetMapping("{id}")
  public ResponseEntity<PokemonResource> show(
      @PathVariable BigInteger id, @RequestParam(required = false) Set<String> include) {
    PokemonResource pokemon = this.pokemonService.show(id, include);

    return ResponseEntity.ok(pokemon);
  }

  @PutMapping("{id}")
  public ResponseEntity<PokemonResource> update(
      @PathVariable BigInteger id, @RequestBody @Valid PokemonRequest request) {
    PokemonResource pokemon = this.pokemonService.update(id, request);

    return ResponseEntity.ok(pokemon);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Object> destroy(@PathVariable BigInteger id) {
    this.pokemonService.destroy(id);

    return ResponseEntity.noContent().build();
  }

  @GetMapping("")
  public ResponseEntity<List<PokemonResource>> index() {
    List<PokemonResource> pokemons = this.pokemonService.index();

    return ResponseEntity.ok(pokemons);
  }
}
