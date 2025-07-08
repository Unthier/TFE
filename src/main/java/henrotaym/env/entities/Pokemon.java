package henrotaym.env.entities;

import henrotaym.env.enums.PokemonTypeName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "pokemons")
public class Pokemon {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private BigInteger id;

  private String name;

  private Integer attack;

  private Integer speed;

  private Integer pv;

  private Integer defense;

  private Integer captureRate;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PokemonTypeName type;
}
