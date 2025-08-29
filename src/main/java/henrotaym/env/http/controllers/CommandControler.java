package henrotaym.env.http.controllers;

import henrotaym.env.enums.ProfileName;
import henrotaym.env.http.requests.CommandRequest;
import henrotaym.env.http.resources.CommandResource;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("commands")
@Profile(ProfileName.HTTP)
public class CommandControler {
  private final CommandService commandService;

  @PostMapping("")
  public ResponseEntity<CommandResource> store(@RequestBody @Valid CommandRequest request) {
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
      @PathVariable BigInteger id, @RequestBody @Valid CommandRequest request) {
    CommandResource pokemon = this.commandService.update(id, request);

    return ResponseEntity.ok(pokemon);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Object> destroy(@PathVariable BigInteger id) {
    this.commandService.destroy(id);

    return ResponseEntity.noContent().build();
  }

  @GetMapping("")
  public ResponseEntity<List<CommandResource>> index() {
    List<CommandResource> pokemons = this.commandService.index();

    return ResponseEntity.ok(pokemons);
  }
}
