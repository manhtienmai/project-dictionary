package application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class SpeakTTS extends APIService{

    public SpeakTTS() {
        super("ce6578568eec4b1a95672c4654fb0cf1",
                "https://eastus.tts.speech.microsoft.com/cognitiveservices/v1",
                "eastus");
    }

    @Override
    public String makeRequestAPI(String text) {
        return synthesizeText(text);
    }
    public String synthesizeText(String text) {
        String ttsUrl = "https://" + region + ".tts.speech.microsoft.com/cognitiveservices/v1";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(ttsUrl);
            request.setHeader("Ocp-Apim-Subscription-Key", apiKey);
            request.setHeader("Content-Type", "application/ssml+xml");
            request.setHeader("X-Microsoft-OutputFormat", "audio-16khz-128kbitrate-mono-mp3");

            String xmlBody = "<speak version='1.0' xml:lang='en-US'><voice xml:lang='en-US' xml:gender='Female' name='en-US-JessaNeural'>" + text + "</voice></speak>";
            StringEntity requestEntity = new StringEntity(xmlBody);
            request.setEntity(requestEntity);

            HttpResponse response = httpClient.execute(request);

            if (response.getStatusLine().getStatusCode() == 200) {
                InputStream audioStream = response.getEntity().getContent();
                File tempFile = File.createTempFile("tts", ".mp3");
                tempFile.deleteOnExit();
                try (FileOutputStream outStream = new FileOutputStream(tempFile)) {
                    audioStream.transferTo(outStream);
                }

                Media media = new Media(tempFile.toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
                return "Success!";
            } else {
                System.err.println("Error: " + response.getStatusLine().getStatusCode());
                return "Error tts";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Exception tss";
        }
    }

//    public static void main(String[] args) {
//        launch(args);
//    }
}