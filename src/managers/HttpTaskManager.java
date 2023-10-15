package managers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import httpServers.KVTaskClient;
import httpServers.LocalDateTimeAdapter;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class HttpTaskManager extends FileBackedTasksManager {
    private final Gson gson = Managers.getGson();
    private final KVTaskClient kvTaskClient;
    private static final String TASK = "task";
    private static final String SUBTASK = "subtask";
    private static final String EPIC = "epic";


    public HttpTaskManager(String url, KVTaskClient kvTaskClient) throws InterruptedException {
        super(url);
        this.kvTaskClient = kvTaskClient;
    }

    public void loadFromServer() throws InterruptedException {
        System.out.println(kvTaskClient.load(TASK));
        List<Task> tasksFromJson = gson.fromJson(kvTaskClient.load(TASK), new TypeToken<List<Task>>(){}.getType());
        if(tasksFromJson != null) {
            for (Task task : tasksFromJson) {
                tasks.put(task.getId(), task);
            }
        }

        List<Epic> epicsFromJson = gson.fromJson(kvTaskClient.load(EPIC), new TypeToken<List<Epic>>() {}.getType());
        if(epicsFromJson != null) {
            for (Epic epic : epicsFromJson) {
                epics.put(epic.getId(), epic);
            }
        }

        List<Subtask> subtasksFromJson = gson.fromJson(kvTaskClient.load(SUBTASK), new TypeToken<List<Subtask>>() {}.getType());
        if(subtasksFromJson != null) {
            for (Subtask subtask : subtasksFromJson) {
                subtasks.put(subtask.getId(), subtask);
            }
        }
    }

    @Override
    public void addTask(Task task) throws InterruptedException {
        super.addTask(task);
        kvTaskClient.put(TASK, gson.toJson(super.getTasks()));
    }

    @Override
    public void addEpic(Epic epic) throws InterruptedException {
        super.addEpic(epic);
        kvTaskClient.put(TASK, gson.toJson(super.getEpics()));
    }

    @Override
    public void addSubtask(Subtask subtask) throws InterruptedException {
        super.addSubtask(subtask);
        kvTaskClient.put(SUBTASK, gson.toJson(super.getSubtasks()));
    }

    @Override
    public Task getTask(int id) throws InterruptedException {
        System.out.println(kvTaskClient.load(TASK));
        List<Task> tasks = gson.fromJson(kvTaskClient.load(TASK), new TypeToken<List<Task>>(){}.getType());
        return tasks.stream().filter(c -> String.valueOf(c.getId()).equals(String.valueOf(id))).findFirst().orElse(null);
    }

    @Override
    public Epic getEpic(int id) throws InterruptedException {
        List<Epic> epics = gson.fromJson(kvTaskClient.load(EPIC), new TypeToken<List<Epic>>(){}.getType());
        return epics.stream().filter(c -> String.valueOf(c.getId()).equals(String.valueOf(id))).findFirst().orElse(null);
    }

    @Override
    public Subtask getSubtask(int id) throws InterruptedException {
        List<Subtask> subtasks = gson.fromJson(kvTaskClient.load(SUBTASK), new TypeToken<List<Subtask>>(){}.getType());
        return subtasks.stream().filter(c -> String.valueOf(c.getId()).equals(String.valueOf(id))).findFirst().orElse(null);
    }
}

