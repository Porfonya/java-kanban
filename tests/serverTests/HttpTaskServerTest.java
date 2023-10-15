package serverTests;

import com.google.gson.Gson;
import enumtype.Status;
import enumtype.TaskType;
import httpServers.HttpTaskServer;
import httpServers.KVServer;
import managers.Managers;
import managers.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import java.net.URI;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTest {
    private TaskManager manager;
    private KVServer kvServer;
    private HttpTaskServer httpTaskServer;
    Task task1 = new Task(1,"name1","description1",  Status.NEW, TaskType.TASK, LocalDateTime.now(), 1);
    Task task2 = new Task(2, "name2","description2",  Status.NEW, TaskType.TASK,LocalDateTime.now(), 2);
    Epic epic1 = new Epic(3,"name1", "description1",  Status.NEW,TaskType.EPIC,
            LocalDateTime.now(), 3);
    Epic epic2 = new Epic(4, "name2","description2",  Status.NEW,TaskType.EPIC,
            LocalDateTime.now(), 4);
    Epic epic3 = new Epic(5, "name1","description1",  Status.NEW, TaskType.SUBTASK,
            LocalDateTime.now(), 5);
    Subtask subtask1 = new Subtask(6, "name1", "description1", Status.NEW, TaskType.SUBTASK,
            10, LocalDateTime.now(),  epic3.getId());
    Subtask subtask2 = new Subtask(7, "name2", "description2", Status.NEW, TaskType.SUBTASK,
            10, LocalDateTime.now(), epic3.getId());

    private static final Gson gson = Managers.getGson();
    private final HttpClient client = HttpClient.newHttpClient();
    private final HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

    @BeforeEach
    public void startServers() throws IOException, InterruptedException {
        kvServer = new KVServer();
        kvServer.start();
        manager = Managers.getDefault("http://localhost:8078");
        httpTaskServer = new HttpTaskServer();
        httpTaskServer.start();
    }

    @Test
    void shouldPOSTTask() throws IOException, InterruptedException {
        URI uri = URI.create("http://localhost:8080/tasks/task/");
        String json = gson.toJson(task1);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(body)
                .uri(uri)
                .build();
        HttpResponse<String> response = client.send(request, handler);

        assertEquals(task1.toString(), manager.getTask(task1.getId()).toString());
        assertEquals(201, response.statusCode());
    }

}