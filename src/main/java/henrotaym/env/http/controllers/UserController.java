package henrotaym.env.http.controllers;

import henrotaym.env.entities.User;
import henrotaym.env.enums.ProfileName;
import henrotaym.env.http.requests.UserRequest;
import henrotaym.env.http.resources.FightResource;
import henrotaym.env.http.resources.PokemonCatchingResource;
import henrotaym.env.http.resources.UserResource;
import henrotaym.env.services.AuthSercive;
import henrotaym.env.services.FightService;
import henrotaym.env.services.PokemonCatchingService;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("users")
@Profile(ProfileName.HTTP)
public class UserController {
  private final UserService userService;
  private final PokemonCatchingService pokemonCatchingService;
  private final FightService fightService;
  private final AuthSercive authSercive;

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
    List<UserResource> users = this.userService.index();

    return ResponseEntity.ok(users);
  }

  @PostMapping("{id}/catch")
  public ResponseEntity<UserResource> catchPokemon(@PathVariable BigInteger id) {
    UserResource user = this.pokemonCatchingService.catchPokemon(id);
    return ResponseEntity.status(HttpStatus.CREATED).body(user);
  }

  @PostMapping("/catch")
  public ResponseEntity<UserResource> catchPokemon(
      @RequestHeader("Authorization") String bearerToken) {
    String token = bearerToken.replace("Bearer ", "");
    User user = this.authSercive.getUserFromToken(token);

    UserResource userResource = this.pokemonCatchingService.catchPokemon(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(userResource);
  }

  @GetMapping("{userid}/pokemons")
  public ResponseEntity<List<PokemonCatchingResource>> indexPokemon(
      @PathVariable BigInteger userid) {
    List<PokemonCatchingResource> pokemonCatchingResources =
        this.pokemonCatchingService.index(userid);
    return ResponseEntity.ok(pokemonCatchingResources);
  }

  @GetMapping("{userId}/pokemons/{pokemonId}")
  public ResponseEntity<PokemonCatchingResource> shoxPokemon(
      @PathVariable BigInteger userId, @PathVariable BigInteger pokemonId) {
    PokemonCatchingResource pokemonCatchingResource =
        this.pokemonCatchingService.show(userId, pokemonId);
    return ResponseEntity.ok(pokemonCatchingResource);
  }

  @PostMapping("{userId}/pokemons/{pokemonId}/abandon")
  public ResponseEntity<PokemonCatchingResource> abandonPokemon(
      @PathVariable BigInteger userId, @PathVariable BigInteger pokemonId) {
    PokemonCatchingResource pokemonCatchingResource =
        this.pokemonCatchingService.abandon(userId, pokemonId);
    return ResponseEntity.ok(pokemonCatchingResource);
  }

  @PostMapping("{userId}/pokemons/{pokemonId}/training")
  public ResponseEntity<PokemonCatchingResource> trainingPokemon(
      @PathVariable BigInteger userId, @PathVariable BigInteger pokemonId) {
    PokemonCatchingResource pokemonCatchingResource =
        this.pokemonCatchingService.training(userId, pokemonId);
    return ResponseEntity.ok(pokemonCatchingResource);
  }

  @PostMapping("{userId}/fights/pokemons/{pokemonId}/trainer/{trainerId}")
  public String fightPokemonStart(
      @PathVariable BigInteger userId,
      @PathVariable BigInteger pokemonId,
      @PathVariable BigInteger trainerId) {

    return this.fightService.store(userId, pokemonId, trainerId);
  }

  @PostMapping("{userId}/fights/{fightId}/abandon")
  public String fightAbandon(@PathVariable BigInteger userId, @PathVariable BigInteger fightId) {
    return this.fightService.fightAbandoned(userId, fightId);
  }

  @GetMapping("{userId}/fights")
  public ResponseEntity<List<FightResource>> indexFights(@PathVariable BigInteger userId) {
    List<FightResource> fights = this.fightService.index(userId);
    return ResponseEntity.ok(fights);
  }

  @PostMapping("{userId}/fights/continue")
  public String fightContinue(@PathVariable BigInteger userId) {

    return this.fightService.fightContinue(userId);
  }
}
