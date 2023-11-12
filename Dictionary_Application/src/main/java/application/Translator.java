package application;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Translator {
    private final String subscriptionKey = "416825b5dc89445dab1b6828bebcd8f9";
    private final String endpoint = "https://api.cognitive.microsofttranslator.com";
    private final String region = "southeastasia";

    public String translate(String from, String to, String text) {
        HttpClient client = HttpClient.newHttpClient();
        String url = endpoint + "/translate?api-version=3.0&from=" + from + "&to=" + to;
        String requestBody = "[{\"Text\":\"" + text + "\"}]";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Ocp-Apim-Subscription-Key", subscriptionKey)
                .header("Ocp-Apim-Subscription-Region", region)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return extractTranslation(response.body());
        } catch (Exception e) {
            e.printStackTrace();
            return "Error in translation";
        }
    }

    private String extractTranslation(String responseBody) {
        try {
            JsonElement jsonElement = JsonParser.parseString(responseBody);
            if (jsonElement == null || !jsonElement.isJsonArray()) {
                return "Invalid response format";
            }

            JsonArray jsonArray = jsonElement.getAsJsonArray();
            JsonObject firstElement = jsonArray.get(0).getAsJsonObject();
            JsonArray translations = firstElement.getAsJsonArray("translations");
            JsonObject firstTranslation = translations.get(0).getAsJsonObject();
            return firstTranslation.get("text").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error parsing translation response";
        }
    }

    public static void main(String[] args) {
        Translator translator = new Translator();
        String translatedText = translator.translate("en", "vi", "nation");
        System.out.println(translatedText);
    }
}

