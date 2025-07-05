package henrotaym.env.http.controllers;

import henrotaym.env.enums.ProfileName;
import henrotaym.env.http.requests.TrainerRequest;
import henrotaym.env.http.resources.TrainerResource;
import henrotaym.env.services.TrainerService;
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
@RequestMapping("trainers")
@Profile(ProfileName.HTTP)
public class TrainerController {
  private final TrainerService trainerService;

  @PostMapping("")
  public ResponseEntity<TrainerResource> store(@RequestBody @Valid TrainerRequest request) {
    TrainerResource trainer = this.trainerService.store(request);

    return ResponseEntity.status(HttpStatus.CREATED).body(trainer);
  }

  @GetMapping("{id}")
  public ResponseEntity<TrainerResource> show(
      @PathVariable BigInteger id, @RequestParam(required = false) Set<String> include) {
    TrainerResource trainer = this.trainerService.show(id, include);

    return ResponseEntity.ok(trainer);
  }

  @PutMapping("{id}")
  public ResponseEntity<TrainerResource> update(
      @PathVariable BigInteger id, @RequestBody @Valid TrainerRequest request) {
    TrainerResource trainer = this.trainerService.update(id, request);

    return ResponseEntity.ok(trainer);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Object> destroy(@PathVariable BigInteger id) {
    this.trainerService.destroy(id);

    return ResponseEntity.noContent().build();
  }

  @GetMapping("")
  public ResponseEntity<List<TrainerResource>> index() {
    List<TrainerResource> trainer = this.trainerService.index();

    return ResponseEntity.ok(trainer);
  }
}
