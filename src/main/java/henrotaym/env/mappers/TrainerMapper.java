package henrotaym.env.mappers;

import henrotaym.env.entities.Trainer;
import henrotaym.env.http.requests.TrainerRequest;
import henrotaym.env.http.requests.relationships.TrainerRelationshipRequest;
import henrotaym.env.http.resources.TrainerResource;
import org.springframework.stereotype.Component;

@Component
public class TrainerMapper {
  public TrainerResource resource(Trainer trainer) {
    return new TrainerResource(trainer.getId(), trainer.getName());
  }

  public Trainer request(TrainerRequest request, Trainer trainer) {
    trainer.setName(request.name());

    return trainer;
  }

  public TrainerRelationshipRequest relationshipRequest(Trainer trainer) {
    return new TrainerRelationshipRequest(trainer.getId());
  }
}
