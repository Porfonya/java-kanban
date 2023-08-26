package managers;
import enumtype.Status;
import tasks.*;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private int elementId = 1;

    private static final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    @Override
    public Map<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    @Override
    public Map<Integer, Epic> getEpics() {
        return epics;
    }

    @Override
    public Task getTask(int taskId) {
        // Получение по идентификатору.
        Task task = null;
        if (tasks.containsKey(taskId)) {
            task = tasks.get(taskId);
            historyManager.add(task);

        }
        return task;
    }

    @Override
    public Epic getEpic(int epicId) {
        // Получение по идентификатору.
        Epic epic = null;
        if (epics.containsKey(epicId)) {
            epic = epics.get(epicId);
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public Subtask getSubtask(int subtaskId) {
        // Получение по идентификатору.
        Subtask subtask = null;
        if (subtasks.containsKey(subtaskId)) {
            subtask = subtasks.get(subtaskId);
            historyManager.add(subtask);
        }
        return subtask;
    }

    @Override
    public void clearAllTask() {
        tasks.clear();
    }

    @Override
    public void clearAllEpic() {

        if (!subtasks.isEmpty()) {

            epics.clear();
            subtasks.clear();
        } else {
            epics.clear();
        }
    }

    @Override
    public void clearAllSubtask() {
        subtasks.clear();
    }

    @Override
    public void addTask(Task task) {
        //Создание. Сам объект должен передаваться в качестве параметра.
        if (task.getId() == 0) {
            task.setId(elementId);
            elementId++;
        }
        tasks.put(task.getId(), task);

    }

    @Override
    public void addEpic(Epic epic) {
        if (epic.getId() == 0) {
            epic.setId(elementId);
            elementId++;
        }
        epics.put(epic.getId(), epic);
    }

    @Override
    public void addSubtask(Subtask subtask) {
        if (epics.containsKey(subtask.getEpicId())) {
            if (subtask.getId() == 0) {
                subtask.setId(elementId);
                elementId++;
            }
            subtasks.put(subtask.getId(), subtask);
            Epic epic = epics.get(subtask.getEpicId());
            epic.getSubtasksIdForEpic().add(subtask.getId());


        } else {
            System.out.println("Подзадача " + subtask + " не создана. Нужно создать epic c id: " + subtask.getEpicId());
        }
    }

    @Override
    public void removeByIdTask(int id) {
        //Удаление по идентификатору.
        if (tasks.containsKey(id)) {
            tasks.remove(id);
            System.out.println("Задача удалена");
        } else {
            System.out.println("Такой задачи нет");
        }
    }

    @Override
    public void removeByIdSubtask(int id) {
        //Удаление по идентификатору.
        if (subtasks.containsKey(id)) {
            subtasks.remove(id);
            System.out.println("Подзадача удалена");
        } else {
            System.out.println("Такой подзадачи нет");
        }
    }

    @Override
    public void removeByIdEpic(int id) {
        //Удаление по идентификатору.
        if (epics.containsKey(id)) {
            Epic epic = epics.remove(id);
            if (epic != null) {
                for (Integer subtaskId : epic.getSubtasksIdForEpic()) {
                    subtasks.remove(subtaskId);
                }
            }
        } else {
            System.out.println("Такой задачи нет");
        }
    }

    @Override
    public List<Subtask> getListByEpic(int epicId) {
        //Получение списка всех подзадач определённого эпика.
        List<Subtask> resultSubtask = new ArrayList<>();
        Epic epic = epics.get(epicId);
        for (Integer subtaskId : epic.getSubtasksIdForEpic()) {
            resultSubtask.add(subtasks.get(subtaskId));

        }
        return resultSubtask;
    }

    @Override
    public void updateTask(Task task) {
        // управление статусами
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        } else {
            System.out.println("Такой задачи не существует");
        }

    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
        } else {
            System.out.println("Такой подзадачи не существует");
        }
    }

    @Override
    public void updateEpic(Epic epic) {

        if (epics.containsKey(epic.getId())) {
            getEpicStatus(epic.getId());
            epics.put(epic.getId(), epic);
        }
    }

    public Status getEpicStatus(int epicId) {
        Status epicStatus = Status.IN_PROGRESS;
        List<Subtask> tmpSubtask = new ArrayList<>();
        Epic epic = epics.get(epicId);
        for (Integer subtaskId : epic.getSubtasksIdForEpic()) {
            tmpSubtask.add(subtasks.get(subtaskId));

        }
        int countStatusNew = 0;
        int countStatusDone = 0;

        for (Subtask subtask : tmpSubtask) {
            if (subtask.status.equals(Status.DONE)) {
                countStatusDone++;
            } else if (subtask.status.equals(Status.NEW)) {
                countStatusNew++;
            }
        }
        if (countStatusDone == tmpSubtask.size()) {
            epicStatus = Status.DONE;
        } else if (countStatusNew == tmpSubtask.size()) {
            epicStatus = Status.NEW;
        }

        return epicStatus;
    }

    public List<Task> getHistory(){
        return historyManager.getHistory();
    }
}
