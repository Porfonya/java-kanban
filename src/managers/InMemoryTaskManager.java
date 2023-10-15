package managers;

import enumtype.Status;
import exceprions.ManagerSaveException;
import tasks.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, Subtask> subtasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();
    private int elementId = 1;

    private static final HistoryManager historyManager = Managers.getDefaultHistory();


    private final TreeSet<Task> prioritizedTasks = new TreeSet<>(
            Comparator.comparing(Task::getStartTime,
                    Comparator.nullsLast(Comparator.naturalOrder())).thenComparingInt(Task::getId)
    );


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
    public void addTask(Task task) throws ManagerSaveException {

        if (task.getId() == 0) {
            task.setId(elementId);
            elementId++;
        }
        prioritizedTasks.add(task);
        if (!isCheckIn()) {
            tasks.put(task.getId(), task);
        } else {
            prioritizedTasks.remove(task);
        }


    }

    @Override
    public void addEpic(Epic epic) throws ManagerSaveException {
        if (epic.getId() == 0) {
            epic.setId(elementId);
            elementId++;
        }
        epics.put(epic.getId(), epic);
    }

    @Override
    public void addSubtask(Subtask subtask) throws ManagerSaveException {
        if (epics.containsKey(subtask.getEpicId())) {
            if (subtask.getId() == 0) {
                subtask.setId(elementId);
                elementId++;
            }
            prioritizedTasks.add(subtask);
            if (!isCheckIn()) {
                subtasks.put(subtask.getId(), subtask);
                Epic epic = epics.get(subtask.getEpicId());
                epic.getSubtasksIdForEpic().add(subtask.getId());
            } else {
                prioritizedTasks.remove(subtask);
            }
        } else {
            System.out.println("Подзадача " + subtask + " не создана. Нужно создать epic c id: " + subtask.getEpicId());
        }
    }

    @Override
    public void removeByIdTask(int id) {
        //Удаление по идентификатору.
        if (tasks.containsKey(id)) {
            tasks.remove(id);
            historyManager.remove(id);
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
            historyManager.remove(id);
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
            historyManager.remove(id);
            if (epic != null) {
                for (Integer subtaskId : epic.getSubtasksIdForEpic()) {
                    subtasks.remove(subtaskId);
                    historyManager.remove(subtaskId);
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
            prioritizedTasks.remove(task);
            prioritizedTasks.add(task);
            if (!isCheckIn()){
                tasks.put(task.getId(), task);
            } else{
                prioritizedTasks.remove(task);
            }


        } else {
            System.out.println("Такой задачи не существует");
        }

    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            prioritizedTasks.remove(subtask);
            prioritizedTasks.add(subtask);
            if (!isCheckIn()){
                subtasks.put(subtask.getId(), subtask);
            }
           else{
               prioritizedTasks.remove(subtask);
            }


        } else {
            System.out.println("Такой подзадачи не существует");
        }
    }

    @Override
    public void updateEpic(Epic epic) {

        if (epics.containsKey(epic.getId())) {
            getEpicStatus(epic.getId());
            epic.setStartTime(getEpicStartTime(epic.getId()));
            epic.setDuration(getEpicDuration(epic.getId()));
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

    @Override
    public long getEpicDuration(int id) {
        long result = 0;
        List<Subtask> subtasks = getListByEpic(id);
        for (Subtask sub : subtasks) {
            result += sub.getDuration();
        }
        return result;
    }

    @Override
    public LocalDateTime getEpicStartTime(int id) {
        List<Subtask> subtasksValue = getListByEpic(id);
        if(subtasksValue.isEmpty()){
            return null;
        }
        LocalDateTime result = subtasksValue.get(0).getStartTime();
        for (Subtask subtask : subtasksValue) {
            if (subtask.getStartTime()!=null){
                if (result == null){
                    result = subtask.getStartTime();
                    continue;
                }
                if (subtask.getStartTime().isBefore(result)){
                    result = subtask.getStartTime();
                }
            }
        }
        return result;
    }

    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    @Override
    public boolean isCheckIn() {
        boolean result = false;
       Task lastTask = null;
        for (Task prioritizedTask : prioritizedTasks) {
            if (lastTask == null){
                lastTask = prioritizedTask;
                continue;
            }
            if (lastTask.getEndTime() == null || prioritizedTask.getStartTime() == null){
                continue;
            }
            if (lastTask.getEndTime().isAfter(prioritizedTask.getStartTime())){
                result = true;
            }
            lastTask = prioritizedTask;
        }
        return result;
    }


    public List<Task> getHistory() {
        return historyManager.getHistory();
    }


}
