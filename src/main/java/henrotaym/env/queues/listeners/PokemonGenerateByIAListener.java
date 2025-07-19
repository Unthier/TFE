package henrotaym.env.queues.listeners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import henrotaym.env.annotations.KafkaRetryableListener;
import henrotaym.env.entities.Pokemon;
import henrotaym.env.enums.ProfileName;
import henrotaym.env.queues.events.PokemonGenerateByIAEvent;
import henrotaym.env.repositories.PokemonRepository;
import io.github.cdimascio.dotenv.Dotenv;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Slf4j
@Component
@Profile(ProfileName.QUEUE)
public class PokemonGenerateByIAListener implements Listener<PokemonGenerateByIAEvent> {

  private final PokemonRepository pokemonRepository;

  @Override
  @KafkaRetryableListener(PokemonGenerateByIAEvent.EVENT_NAME)
  public void listen(PokemonGenerateByIAEvent event) {
    log.info("Pokemon generate by IA event received: {}", event.getMessage());
    List<String> pokemonNames = pokemonRepository.findAll().stream().map(Pokemon::getName).toList();

    log.info("Current Pokémon names: {}", pokemonNames);

    Dotenv dotenv = Dotenv.load();
    String apiKey = dotenv.get("OPENAI_API_KEY");
    HttpClient client = HttpClient.newHttpClient();

    String prompt =
        String.format(
            """
            Tu dois générer un ou plusieurs Pokémon totalement inventés.
            Privilégie les Pokémon sans évolution, mais tu peux en créer avec une ou deux formes évoluées au maximum.

            Voici la structure JSON attendue :
            [
              {
                "name": "Nom unique",
                "type": "Un des types suivants : NORMAL, FIRE, WATER, GRASS, ELECTRIC, ICE, FIGHTING, POISON, GROUND, FLYING, PSYCHIC, BUG, ROCK, GHOST, DARK, DRAGON, STEEL, FAIRY",
                "pv": 70,
                "attack": 80,
                "defense": 60,
                "speed": 75,
                "captureRate": 50,
                "nextEvolutionLevel": null,
                "nameOfNextEvolution": null
              }
            ]

            Règles :
            - Réponds uniquement avec un tableau JSON, sans aucune explication ni commentaire.
            - Si tu veux créer une évolution, ajoute un deuxième objet dans le tableau représentant la forme évoluée.
            - Si un Pokémon évolue une deuxième fois (maximum 3 formes au total), ajoute un troisième objet.
            - Le champ `nameOfNextEvolution` doit être `null` pour le dernier stade d’évolution.
            - J'ai déjà ça comme nom de Pokémon : %s
            """,
            pokemonNames);

    ObjectMapper mapper = new ObjectMapper();

    try {
      // Construction du corps de la requête
      ObjectNode root = mapper.createObjectNode();
      root.put("model", "gpt-3.5-turbo");
      root.put("temperature", 1);

      ArrayNode messages = mapper.createArrayNode();

      ObjectNode systemMessage = mapper.createObjectNode();
      systemMessage.put("role", "system");
      systemMessage.put("content", "Tu es une IA qui crée des Pokémon inventés.");
      messages.add(systemMessage);

      ObjectNode userMessage = mapper.createObjectNode();
      userMessage.put("role", "user");
      userMessage.put("content", prompt);
      messages.add(userMessage);

      root.set("messages", messages);

      String requestBody = mapper.writeValueAsString(root);

      HttpRequest request =
          HttpRequest.newBuilder()
              .uri(URI.create("https://api.openai.com/v1/chat/completions"))
              .header("Content-Type", "application/json")
              .header("Authorization", "Bearer " + apiKey)
              .POST(HttpRequest.BodyPublishers.ofString(requestBody))
              .build();

      client
          .sendAsync(request, HttpResponse.BodyHandlers.ofString())
          .thenApply(HttpResponse::body)
          .thenAccept(
              response -> {
                log.info("IA response: {}", response);
                try {
                  JsonNode rootResponse = mapper.readTree(response);
                  String content =
                      rootResponse.get("choices").get(0).get("message").get("content").asText();

                  List<Pokemon> pokemons =
                      Arrays.asList(mapper.readValue(content, Pokemon[].class));

                  pokemons.forEach(
                      pokemon -> {
                        log.info("Saving Pokémon: {}", pokemon);
                        pokemonRepository.save(pokemon);
                      });
                } catch (Exception e) {
                  log.error("Erreur lors de l’analyse de la réponse de l’IA", e);
                }
              });

    } catch (Exception e) {
      log.error("Erreur lors de la préparation de la requête OpenAI", e);
    }
  }
}
