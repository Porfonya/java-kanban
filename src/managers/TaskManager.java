package managers;

import enumtype.Status;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.*;

public interface TaskManager {

    Map<Integer, Task> getTasks();

    Map<Integer, Subtask> getSubtasks();

    Map<Integer, Epic> getEpics();

    Task getTask(int taskId);

    Epic getEpic(int epicId);

    Subtask getSubtask(int subtaskId);

    void clearAllTask();

    void clearAllEpic();

    void clearAllSubtask();

    void addTask(Task task);

    void addEpic(Epic epic);

    void addSubtask(Subtask subtask);

    void removeByIdTask(int id);

    void removeByIdSubtask(int id);

    void removeByIdEpic(int id);

    List<Subtask> getListByEpic(int epicId);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    Status getEpicStatus(int epicId);


}
