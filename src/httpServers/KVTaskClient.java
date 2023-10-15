package httpServers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {

    private final HttpClient httpClient;
    private String url;
    private final String apiToken;

    public KVTaskClient(String _url) throws InterruptedException {
        url = _url;
        httpClient = HttpClient.newHttpClient();
        apiToken = register();
    }

    public void put(String key, String json) throws InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri((URI.create(url + "/save/" + key + "?API_TOKEN=" + apiToken)))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        try {
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (NullPointerException | IOException | InterruptedException e) {
            throw new InterruptedException("Во время выполнения запроса возникла ошибка.");
        }
    }

    public String register() throws InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri((URI.create(url + "/register")))
                .GET()
                .build();
        try {
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (NullPointerException | IOException | InterruptedException e) {
            throw new InterruptedException("Во время выполнения запроса возникла ошибка.");
        }
    }

    public String load(String key) throws InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri((URI.create(url + "/load/" + key + "?API_TOKEN=" + apiToken)))
                .GET()
                .build();
        try {
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.body() == null) {
                return "";
            }
            return response.body();
        } catch (NullPointerException | IOException | InterruptedException e) {
            throw new InterruptedException("Во время выполнения запроса возникла ошибка.");
        }
    }
}
