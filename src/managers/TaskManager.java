package managers;

import enumtype.Status;
import tasks.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

public interface TaskManager {

    Map<Integer, Task> getTasks();

    Map<Integer, Subtask> getSubtasks();

    Map<Integer, Epic> getEpics();

    Task getTask(int taskId) throws InterruptedException;

    Epic getEpic(int epicId) throws InterruptedException;

    Subtask getSubtask(int subtaskId) throws InterruptedException;

    void clearAllTask();

    void clearAllEpic();

    void clearAllSubtask();

    void addTask(Task task) throws InterruptedException;

    void addEpic(Epic epic) throws InterruptedException;

    void addSubtask(Subtask subtask) throws InterruptedException;

    void removeByIdTask(int id);

    void removeByIdSubtask(int id);

    void removeByIdEpic(int id);

    List<Subtask> getListByEpic(int epicId);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    Status getEpicStatus(int epicId);

    long getEpicDuration(int id);

    LocalDateTime getEpicStartTime(int id);

    TreeSet<Task> getPrioritizedTasks();

    boolean isCheckIn();

}
