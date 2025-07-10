package henrotaym.env.http.controllers;

import henrotaym.env.enums.ProfileName;
import henrotaym.env.http.requests.UserRequest;
import henrotaym.env.http.resources.UserResource;
import henrotaym.env.services.PokemonService;
import henrotaym.env.services.UserService;
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
@RequestMapping("users")
@Profile(ProfileName.HTTP)
public class UserController {
  private final UserService userService;
  private final PokemonService pokemonService;

  @PostMapping("")
  public ResponseEntity<UserResource> store(@RequestBody @Valid UserRequest request) {
    UserResource user = this.userService.store(request);

    return ResponseEntity.status(HttpStatus.CREATED).body(user);
  }

  @GetMapping("{id}")
  public ResponseEntity<UserResource> show(
      @PathVariable BigInteger id, @RequestParam(required = false) Set<String> include) {
    UserResource pokemon = this.userService.show(id, include);

    return ResponseEntity.ok(pokemon);
  }

  @PutMapping("{id}")
  public ResponseEntity<UserResource> update(
      @PathVariable BigInteger id, @RequestBody @Valid UserRequest request) {
    UserResource pokemon = this.userService.update(id, request);

    return ResponseEntity.ok(pokemon);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Object> destroy(@PathVariable BigInteger id) {
    this.userService.destroy(id);

    return ResponseEntity.noContent().build();
  }

  @GetMapping("")
  public ResponseEntity<List<UserResource>> index() {
    List<UserResource> pokemons = this.userService.index();

    return ResponseEntity.ok(pokemons);
  }

  @PostMapping("{id}/catch")
  public ResponseEntity<UserResource> catchPokemon(@PathVariable BigInteger id) {
    UserResource user = this.userService.catchPokemon(id);
    return ResponseEntity.status(HttpStatus.CREATED).body(user);
  }
}
