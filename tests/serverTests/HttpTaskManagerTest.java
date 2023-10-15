package serverTests;

import managers.FileBackedTasksManager;
import managers.InMemoryTaskManager;
import managers.Managers;
import managers.TaskManager;
import org.junit.jupiter.api.*;
import servers.HttpTaskManager;
import servers.KVServer;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpTaskManagerTest {
  private static KVServer kvServer;
    Task task = new Task("Task1", "Description1");
    Epic epic = new Epic("Epic1", "EpicDescription");
    Subtask subtask = new Subtask("Subtask", "SubtaskDescription", 5);

    @BeforeEach
    void setManager() throws InterruptedException {
     TaskManager manager = (HttpTaskManager) Managers.getDefault("http://localhost:8078/");
    }

    @BeforeAll
    static void startServer() throws IOException {
        kvServer = new KVServer();
        kvServer.start();
    }

    @Test
    void shouldLoadFromServer() throws InterruptedException {
        HttpTaskManager manager = new HttpTaskManager("http://localhost:8078/");
        manager.addTask(task);
        manager.addEpic(epic);
        manager.addSubtask(subtask);
        manager.getTask(task.getId());


        assertEquals(2, manager.getPrioritizedTasks().size());
        assertEquals(1, manager.getHistory().size());
        assertEquals(task.toString(), manager.getTask(task.getId()).toString());
        assertEquals(epic.toString(), manager.getEpic(epic.getId()).toString());
        assertEquals(subtask.toString(), manager.getSubtask(subtask.getId()).toString());
    }

    @AfterAll
    static void stopServer() {
        kvServer.stop();
    }
}
