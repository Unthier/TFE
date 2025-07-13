package henrotaym.env.http.controllers;

import henrotaym.env.enums.ProfileName;
import henrotaym.env.http.requests.PokemonTrainerRequest;
import henrotaym.env.http.resources.PokemonTrainerResource;
import henrotaym.env.services.PokemonTrainerService;
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
@RequestMapping("trainersPokemons")
@Profile(ProfileName.HTTP)
public class PokemonTrainerController {
  private final PokemonTrainerService pokemonTrainerService;

  @PostMapping("")
  public ResponseEntity<PokemonTrainerResource> store(
      @RequestBody @Valid PokemonTrainerRequest request) {
    PokemonTrainerResource trainer = this.pokemonTrainerService.store(request);

    return ResponseEntity.status(HttpStatus.CREATED).body(trainer);
  }

  @GetMapping("{id}")
  public ResponseEntity<PokemonTrainerResource> show(
      @PathVariable BigInteger id, @RequestParam(required = false) Set<String> include) {
    PokemonTrainerResource trainer = this.pokemonTrainerService.show(id, include);

    return ResponseEntity.ok(trainer);
  }

  @PutMapping("{id}")
  public ResponseEntity<PokemonTrainerResource> update(
      @PathVariable BigInteger id, @RequestBody @Valid PokemonTrainerRequest request) {
    PokemonTrainerResource trainer = this.pokemonTrainerService.update(id, request);

    return ResponseEntity.ok(trainer);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Object> destroy(@PathVariable BigInteger id) {
    this.pokemonTrainerService.destroy(id);

    return ResponseEntity.noContent().build();
  }

  @GetMapping("")
  public ResponseEntity<List<PokemonTrainerResource>> index() {
    List<PokemonTrainerResource> trainer = this.pokemonTrainerService.index();

    return ResponseEntity.ok(trainer);
  }
}
