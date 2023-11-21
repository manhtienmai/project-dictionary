package application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DictionaryAPI {

    // use to parse Json data
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static JsonNode getWords(String word) throws Exception {
        String apiUrl = "https://api.dictionaryapi.dev/api/v2/entries/en/" + word;
        StringBuilder result = new StringBuilder();

        URL url = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        // read response
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            // read each line and add to StringBuilder
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        }

        //convert StringBuilder into JsonNode
        return objectMapper.readTree(result.toString());
    }
}
