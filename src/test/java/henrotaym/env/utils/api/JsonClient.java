package henrotaym.env.utils.api;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.function.ThrowingConsumer;

@Component
@RequiredArgsConstructor
public class JsonClient {
  private final MockMvc mockMvc;
  private final JsonResponse response;
  private final JsonRequest request;
  private final Map<String, String> headers = new HashMap<>();

  public JsonClient request(ThrowingConsumer<JsonRequest> callback) throws Exception {
    callback.acceptWithException(this.request);

    return this;
  }

  public JsonResponse perform() throws Exception {
    ResultActions result = this.mockMvc.perform(this.request.request());

    this.response.setResponse(result);

    return response;
  }

  public JsonRequest header(String name, String value) {
    this.headers.put(name, value);
    return this.header(name, value);
  }
}
