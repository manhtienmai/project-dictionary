package application;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import application.service.APIService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Translation extends APIService {

    public Translation() {
        super("416825b5dc89445dab1b6828bebcd8f9",
                "https://api.cognitive.microsofttranslator.com",
                "southeastasia");
    }

    @Override
    public String makeRequestAPI(String text) {
        return translate("en", "vi", text);
    }

    public String translate(String from, String to, String text) {
        HttpClient client = HttpClient.newHttpClient();
        String url = endpointUrl + "/translate?api-version=3.0&from=" + from + "&to=" + to;
        String requestBody = "[{\"Text\":\"" + text + "\"}]";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Ocp-Apim-Subscription-Key", apiKey)
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
                return "Invalid response";
            }

            JsonArray jsonArray = jsonElement.getAsJsonArray();
            JsonObject firstElement = jsonArray.get(0).getAsJsonObject();
            JsonArray translations = firstElement.getAsJsonArray("translations");
            JsonObject firstTranslation = translations.get(0).getAsJsonObject();
            return firstTranslation.get("text").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error translation response";
        }
    }

    public static void main(String[] args) {
        Translation translation = new Translation();
        String translatedText = translation.makeRequestAPI("nation");
        System.out.println(translatedText);
    }
}

