package henrotaym.env.mappers;

import henrotaym.env.entities.Command;
import henrotaym.env.http.requests.CommandRequest;
import henrotaym.env.http.resources.CommandResource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommandMapper {
  public CommandResource resource(Command command) {
    return new CommandResource(
        command.getId(), command.getMethode(), command.getUrl(), command.getDescript());
  }

  public Command request(CommandRequest request, Command command) {
    command.setUrl(request.url());
    command.setMethode(request.methode());
    command.setDescript(request.descript());

    return command;
  }
}
