package henrotaym.env.services;

import henrotaym.env.entities.Command;
import henrotaym.env.http.requests.CommandRequest;
import henrotaym.env.http.resources.CommandResource;
import henrotaym.env.mappers.ResourceMapper;
import henrotaym.env.repositories.CommandRepository;
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
public class CommandService {
  private CommandRepository commandRepository;
  private ResourceMapper resourceMapper;

  public CommandResource store(CommandRequest request) {
    Command command = new Command();

    return this.storeOrUpdate(request, command);
  }

  public CommandResource update(BigInteger id, CommandRequest request) {
    Command command = this.findById(id);

    return this.storeOrUpdate(request, command);
  }

  public CommandResource show(BigInteger id, Set<String> include) {
    Command command = this.findById(id);

    return this.resourceMapper.commandResource(command);
  }

  public List<CommandResource> index() {
    return this.commandRepository.findAll().stream()
        .map(command -> this.resourceMapper.commandResource(command))
        .toList();
  }

  public void destroy(BigInteger id) {
    Command command = this.findById(id);

    this.commandRepository.delete(command);
  }

  private Command findById(BigInteger id) {
    return this.commandRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Pokémon not found."));
  }

  private CommandResource storeOrUpdate(CommandRequest request, Command command) {
    command = this.resourceMapper.getCommandMapper().request(request, command);
    command = this.commandRepository.save(command);

    return this.resourceMapper.commandResource(command);
  }
}
