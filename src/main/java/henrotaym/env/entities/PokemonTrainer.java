package henrotaym.env.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import henrotaym.env.enums.PokemonTypeName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "pokemons_trainer")
public class PokemonTrainer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private BigInteger id;

  private BigInteger pokemonId;

  private String name;

  private Integer attack;

  private Integer speed;

  private Integer pv;

  private Integer level;

  private Integer defense;

  private Integer damage;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PokemonTypeName type;

  @ManyToOne(optional = false)
  @JoinColumn(name = "trainer_id", nullable = false)
  @JsonBackReference()
  private Trainer trainer;
}
