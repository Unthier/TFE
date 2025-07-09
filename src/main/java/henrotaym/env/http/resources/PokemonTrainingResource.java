package henrotaym.env.http.resources;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import henrotaym.env.enums.PokemonTypeName;
import henrotaym.env.serializers.HasIncludables;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class PokemonTrainingResource implements HasIncludables{

    private final BigInteger id;
  private final LocalDateTime trainingOn;
  private PokemonCatchingResource pokemonCatching;

@Override
    public Set<String> includables() {
        return Set.of("pokemonCatching");
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'includables'");
    }
    
}
