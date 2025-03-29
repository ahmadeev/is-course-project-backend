package _service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto._utils.DiceAPIDTO;
import jakarta.ejb.Stateless;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Stateless
public class ExternalAPIService {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final static String ROLL_D20_DICE_URL = "https://rolz.org/api"; // "https://rolz.org/api/?1d20.json"

    public DiceAPIDTO rollDice(int amount, int value) {
        try {
            // Создаем HTTP-запрос
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ROLL_D20_DICE_URL + "/?" + amount + "d" + value + ".json"))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), DiceAPIDTO.class);
            } else {
                throw new RuntimeException("Ошибка при получении данных: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при запросе к API", e);
        }
    }
}
