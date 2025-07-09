package henrotaym.env.repositories;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import henrotaym.env.entities.PokemonTraining;

public interface PokemonTrainingRepositoy extends JpaRepository<PokemonTraining, BigInteger>{
    
}
