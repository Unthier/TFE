package henrotaym.env.http.controllers;

import henrotaym.env.entities.User;
import henrotaym.env.enums.ProfileName;
import henrotaym.env.enums.UserRoleName;
import henrotaym.env.http.requests.UserRequest;
import henrotaym.env.http.resources.FightResource;
import henrotaym.env.http.resources.PokemonCatchingResource;
import henrotaym.env.http.resources.UserResource;
import henrotaym.env.services.AuthSercive;
import henrotaym.env.services.FightService;
import henrotaym.env.services.PokemonCatchingService;
import henrotaym.env.services.UserService;
import jakarta.validation.Valid;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserController {
  private final UserService userService;
  private final PokemonCatchingService pokemonCatchingService;
  private final FightService fightService;
  private final AuthSercive authSercive;

  @PostMapping("")
  public ResponseEntity<UserResource> store(
      @RequestHeader("Authorization") String bearerToken, @RequestBody @Valid UserRequest request) {
    String token = bearerToken.replace("Bearer ", "");
    User user = this.authSercive.getUserFromToken(token);
    if (user.getRole() != UserRoleName.ADMIN) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
    UserResource userResource = this.userService.store(request);

    return ResponseEntity.status(HttpStatus.CREATED).body(userResource);
  }

  @GetMapping("{id}")
  public ResponseEntity<UserResource> show(
      @RequestHeader("Authorization") String bearerToken,
      @PathVariable BigInteger id,
      @RequestParam(required = false) Set<String> include) {
    String token = bearerToken.replace("Bearer ", "");
    User user = this.authSercive.getUserFromToken(token);
    if (user.getRole() != UserRoleName.ADMIN) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
    UserResource userResource = this.userService.show(id, include);

    return ResponseEntity.ok(userResource);
  }

  @PutMapping("{id}")
  public ResponseEntity<UserResource> update(
      @RequestHeader("Authorization") String bearerToken,
      @PathVariable BigInteger id,
      @RequestBody @Valid UserRequest request) {
    String token = bearerToken.replace("Bearer ", "");
    User user = this.authSercive.getUserFromToken(token);
    if (user.getRole() != UserRoleName.ADMIN) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
    UserResource pokemon = this.userService.update(id, request);

    return ResponseEntity.ok(pokemon);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Object> destroy(
      @RequestHeader("Authorization") String bearerToken, @PathVariable BigInteger id) {
    String token = bearerToken.replace("Bearer ", "");
    User user = this.authSercive.getUserFromToken(token);
    if (user.getRole() != UserRoleName.ADMIN) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body("You are not autorized to delete this user");
    }
    this.userService.destroy(id);

    return ResponseEntity.noContent().build();
  }

  // @GetMapping("")
  // public ResponseEntity<List<UserResource>> index() {
  //   List<UserResource> users = this.userService.index();

  //   return ResponseEntity.ok(users);
  // }

  // @PostMapping("{id}/catch")
  // public ResponseEntity<UserResource> catchPokemon(@PathVariable BigInteger id) {
  //   UserResource user = this.pokemonCatchingService.catchPokemon(id);
  //   return ResponseEntity.status(HttpStatus.CREATED).body(user);
  // }

  @PostMapping("/catch")
  public ResponseEntity<UserResource> catchPokemon(
      @RequestHeader("Authorization") String bearerToken) {
    String token = bearerToken.replace("Bearer ", "");
    User user = this.authSercive.getUserFromToken(token);

    UserResource userResource = this.pokemonCatchingService.catchPokemon(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(userResource);
  }

  // @GetMapping("{userid}/pokemons")
  // public ResponseEntity<List<PokemonCatchingResource>> indexPokemon(
  //     @PathVariable BigInteger userid) {
  //   List<PokemonCatchingResource> pokemonCatchingResources =
  //       this.pokemonCatchingService.index(userid);
  //   return ResponseEntity.ok(pokemonCatchingResources);
  // }

  @GetMapping("/pokemons")
  public ResponseEntity<List<PokemonCatchingResource>> indexPokemon(
      @RequestHeader("Authorization") String bearerToken) {
    String token = bearerToken.replace("Bearer ", "");
    User user = this.authSercive.getUserFromToken(token);
    List<PokemonCatchingResource> pokemonCatchingResources =
        this.pokemonCatchingService.index(user.getId());
    return ResponseEntity.ok(pokemonCatchingResources);
  }

  // @GetMapping("{userId}/pokemons/{pokemonId}")
  // public ResponseEntity<PokemonCatchingResource> showPokemon(
  //     @PathVariable BigInteger userId, @PathVariable BigInteger pokemonId) {
  //   PokemonCatchingResource pokemonCatchingResource =
  //       this.pokemonCatchingService.show(userId, pokemonId);
  //   return ResponseEntity.ok(pokemonCatchingResource);
  // }

  @GetMapping("/pokemons/{pokemonId}")
  public ResponseEntity<PokemonCatchingResource> showPokemon(
      @RequestHeader("Authorization") String bearerToken, @PathVariable BigInteger pokemonId) {
    String token = bearerToken.replace("Bearer ", "");
    User user = this.authSercive.getUserFromToken(token);
    PokemonCatchingResource pokemonCatchingResource =
        this.pokemonCatchingService.show(user.getId(), pokemonId);
    return ResponseEntity.ok(pokemonCatchingResource);
  }

  // @PostMapping("{userId}/pokemons/{pokemonId}/abandon")
  // public ResponseEntity<PokemonCatchingResource> abandonPokemon(
  //     @PathVariable BigInteger userId, @PathVariable BigInteger pokemonId) {
  //   PokemonCatchingResource pokemonCatchingResource =
  //       this.pokemonCatchingService.abandon(userId, pokemonId);
  //   return ResponseEntity.ok(pokemonCatchingResource);
  // }

  @PostMapping("pokemons/{pokemonId}/abandon")
  public ResponseEntity<PokemonCatchingResource> abandonPokemon(
      @RequestHeader("Authorization") String bearerToken, @PathVariable BigInteger pokemonId) {
    String token = bearerToken.replace("Bearer ", "");
    User user = this.authSercive.getUserFromToken(token);
    PokemonCatchingResource pokemonCatchingResource =
        this.pokemonCatchingService.abandon(user.getId(), pokemonId);
    return ResponseEntity.ok(pokemonCatchingResource);
  }

  // @PostMapping("{userId}/pokemons/{pokemonId}/training")
  // public ResponseEntity<PokemonCatchingResource> trainingPokemon(
  //     @PathVariable BigInteger userId, @PathVariable BigInteger pokemonId) {
  //   PokemonCatchingResource pokemonCatchingResource =
  //       this.pokemonCatchingService.training(userId, pokemonId);
  //   return ResponseEntity.ok(pokemonCatchingResource);
  // }

  @PostMapping("pokemons/{pokemonId}/training")
  public ResponseEntity<PokemonCatchingResource> trainingPokemon(
      @RequestHeader("Authorization") String bearerToken, @PathVariable BigInteger pokemonId) {
    String token = bearerToken.replace("Bearer ", "");
    User user = this.authSercive.getUserFromToken(token);
    PokemonCatchingResource pokemonCatchingResource =
        this.pokemonCatchingService.training(user.getId(), pokemonId);
    return ResponseEntity.ok(pokemonCatchingResource);
  }

  // @PostMapping("{userId}/fights/pokemons/{pokemonId}/trainer/{trainerId}")
  // public String fightPokemonStart(
  //     @PathVariable BigInteger userId,
  //     @PathVariable BigInteger pokemonId,
  //     @PathVariable BigInteger trainerId) {

  //   return this.fightService.store(userId, pokemonId, trainerId);
  // }

  @PostMapping("fights/pokemons/{pokemonId}/trainer/{trainerId}")
  public String fightPokemonStart(
      @RequestHeader("Authorization") String bearerToken,
      @PathVariable BigInteger pokemonId,
      @PathVariable BigInteger trainerId) {

    String token = bearerToken.replace("Bearer ", "");
    User user = this.authSercive.getUserFromToken(token);
    return this.fightService.store(user.getId(), pokemonId, trainerId);
  }

  // @PostMapping("{userId}/fights/{fightId}/abandon")
  // public String fightAbandon(@PathVariable BigInteger userId, @PathVariable BigInteger fightId) {
  //   return this.fightService.fightAbandoned(userId, fightId);
  // }

  @PostMapping("fights/{fightId}/abandon")
  public String fightAbandon(
      @RequestHeader("Authorization") String bearerToken, @PathVariable BigInteger fightId) {
    String token = bearerToken.replace("Bearer ", "");
    User user = this.authSercive.getUserFromToken(token);
    return this.fightService.fightAbandoned(user.getId(), fightId);
  }

  // @GetMapping("{userId}/fights")
  // public ResponseEntity<List<FightResource>> indexFights(@PathVariable BigInteger userId) {
  //   List<FightResource> fights = this.fightService.index(userId);
  //   return ResponseEntity.ok(fights);
  // }

  @GetMapping("fights")
  public ResponseEntity<List<FightResource>> indexFights(
      @RequestHeader("Authorization") String bearerToken) {
    String token = bearerToken.replace("Bearer ", "");
    User user = this.authSercive.getUserFromToken(token);
    List<FightResource> fights = this.fightService.index(user.getId());
    return ResponseEntity.ok(fights);
  }

  // @PostMapping("{userId}/fights/continue")
  // public String fightContinue(@PathVariable BigInteger userId) {

  //   return this.fightService.fightContinue(userId);
  // }

  @PostMapping("fights/continue")
  public String fightContinue(@RequestHeader("Authorization") String bearerToken) {
    String token = bearerToken.replace("Bearer ", "");
    User user = this.authSercive.getUserFromToken(token);
    return this.fightService.fightContinue(user.getId());
  }
}
