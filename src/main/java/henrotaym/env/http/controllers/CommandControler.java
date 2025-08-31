package henrotaym.env.http.controllers;

import henrotaym.env.entities.User;
import henrotaym.env.enums.ProfileName;
import henrotaym.env.enums.UserRoleName;
import henrotaym.env.http.requests.CommandRequest;
import henrotaym.env.http.resources.CommandResource;
import henrotaym.env.services.AuthSercive;
import henrotaym.env.services.CommandService;
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
@RequestMapping("commands")
@Profile(ProfileName.HTTP)
public class CommandControler {
  private final CommandService commandService;
  private final AuthSercive authSercive;

  @PostMapping("")
  public ResponseEntity<CommandResource> store(
      @RequestHeader("Authorization") String bearerToken,
      @RequestBody @Valid CommandRequest request) {
    String token = bearerToken.replace("Bearer ", "");
    User user = this.authSercive.getUserFromToken(token);
    if (user.getRole() != UserRoleName.ADMIN) {
      throw new RuntimeException("Only admin can create trainers");
    }
    CommandResource command = this.commandService.store(request);

    return ResponseEntity.status(HttpStatus.CREATED).body(command);
  }

  @GetMapping("{id}")
  public ResponseEntity<CommandResource> show(
      @PathVariable BigInteger id, @RequestParam(required = false) Set<String> include) {
    CommandResource command = this.commandService.show(id, include);

    return ResponseEntity.ok(command);
  }

  @PutMapping("{id}")
  public ResponseEntity<CommandResource> update(
      @RequestHeader("Authorization") String bearerToken,
      @PathVariable BigInteger id,
      @RequestBody @Valid CommandRequest request) {
    String token = bearerToken.replace("Bearer ", "");
    User user = this.authSercive.getUserFromToken(token);
    if (user.getRole() != UserRoleName.ADMIN) {
      throw new RuntimeException("Only admin can create trainers");
    }
    CommandResource pokemon = this.commandService.update(id, request);

    return ResponseEntity.ok(pokemon);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Object> destroy(
      @RequestHeader("Authorization") String bearerToken, @PathVariable BigInteger id) {
    String token = bearerToken.replace("Bearer ", "");
    User user = this.authSercive.getUserFromToken(token);
    if (user.getRole() != UserRoleName.ADMIN) {
      throw new RuntimeException("Only admin can create trainers");
    }
    this.commandService.destroy(id);

    return ResponseEntity.noContent().build();
  }

  @GetMapping("")
  public ResponseEntity<List<CommandResource>> index() {
    List<CommandResource> pokemons = this.commandService.index();

    return ResponseEntity.ok(pokemons);
  }
}
