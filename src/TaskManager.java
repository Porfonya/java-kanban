import java.util.*;

public class TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private int elementId = 1;

    Scanner scanner = new Scanner(System.in);


    public HashMap<Integer, Task> getTasks() {
        return tasks;

    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public String getTask(int taskId) {
        // Получение по идентификатору.
        Task task = null;
        if (tasks.containsKey(taskId)) {
            task = tasks.get(taskId);

        }
        return task != null ? task.toString() : "Задачи с id = " + taskId + " нет.";
    }

    public String getEpic(int epicId) {
        // Получение по идентификатору.
        Epic epic = null;
        if (epics.containsKey(epicId)) {
            epic = epics.get(epicId);
        }
        return epic != null ? epic.toString() : "Эпика с id = " + epicId + " нет.";
    }

    public String getSubtask(int subtaskId) {
        // Получение по идентификатору.
        Subtask subtask = null;
        if (subtasks.containsKey(subtaskId)) {
            subtask = subtasks.get(subtaskId);
        }
        return subtask != null ? subtask.toString() : "Подзадачи с id = " + subtaskId + " нет.";
    }

    public void clearAllTask() {
        while (true) {
            System.out.println("Вы точно хотите удалить все задачи: 'Да / Нет'?");
            String command = scanner.next();
            if (command.equals("Да")) {
                tasks.clear();
                System.out.println("Все задачи удалены");
                return;
            } else if (command.equals("Нет")) {
                System.out.println("До встречи!");
                break;
            }
        }
    }

    public void clearAllEpic() {
        while (true) {
            System.out.println("Вы точно хотите удалить все эпики: 'Да / Нет'?");
            String command = scanner.next();
            if (command.equals("Да")) {
                if (!subtasks.isEmpty()) {
                    System.out.println("Эпики содержат подзадачи. Все равно удалить: 'Да / Нет'?");
                    if (scanner.next().equals("Да")) {
                        epics.clear();
                        subtasks.clear();
                        System.out.println("Все epic и подзадачи удалены");
                        return;
                    } else {
                        break;
                    }
                } else {
                    epics.clear();
                    System.out.println("Все еппики удалены");
                    return;
                }
            } else if (command.equals("Нет")) {
                System.out.println("До встречи!");
                break;
            }
        }
    }

    public void clearAllSubtask() {
        while (true) {
            System.out.println("Вы точно хотите удалить все подзадачи: 'Да / Нет'?");
            String command = scanner.next();
            if (command.equals("Да")) {
                subtasks.clear();
                System.out.println("Все подзадачи удалены");
                return;
            } else if (command.equals("Нет")) {
                System.out.println("До встречи!");
                break;
            }
        }
    }


    public void addTask(Task task) {
        //Создание. Сам объект должен передаваться в качестве параметра.
        if (task.getId() == 0) {
            task.setId(elementId);
            elementId++;
        }
        tasks.put(task.getId(), task);
    }

    public void addEpic(Epic epic) {
        if (epic.getId() == 0) {
            epic.setId(elementId);
            elementId++;
        }
        epics.put(epic.getId(), epic);
    }

    public void addSubtask(Subtask subtask) {

        if (epics.containsKey(subtask.getEpicId())) {
            if (subtask.getId() == 0) {
                subtask.setId(elementId);
                elementId++;
            }
            subtasks.put(subtask.getId(), subtask);

        } else {
            System.out.println("Подзадача " + subtask + " не создана. Нужно создать epic c id: " + subtask.getEpicId());
        }
    }


    public void removeByIdTask(int id) {
        //Удаление по идентификатору.

        if (tasks.containsKey(id)) {
            tasks.remove(id);
            System.out.println("Задача удалена");
        } else {
            System.out.println("Такой задачи нет");
        }
    }

    public void removeByIdSubtask(int id) {
        //Удаление по идентификатору.
        if (subtasks.containsKey(id)) {
            subtasks.remove(id);
            System.out.println("Подзадача удалена");
        } else {
            System.out.println("Такой подзадачи нет");
        }
    }

    public void removeByIdEpic(int id) {
        //Удаление по идентификатору.
        if (epics.containsKey(id)) {
            if (!subtasks.isEmpty()) {
                subtasks.entrySet().removeIf(entry -> entry.getValue().getEpicId() == id);
            }
            epics.remove(id);
            System.out.println("Эпик и связанные подзадачи удалены");
        } else {
            System.out.println("Такой задачи нет");
        }
    }


    public List<Subtask> getListByEpic(int epicId) {
        //Получение списка всех подзадач определённого эпика.
        List<Subtask> resultSubtask = new ArrayList<>();
        if (epics.containsKey(epicId)) {
            for (Subtask subtask : subtasks.values()) {
                if (subtask.getEpicId() == epicId) {
                    resultSubtask.add(subtask);
                }
            }
        }

        return resultSubtask;
    }

    public void updateTask(Task task) {
        // управление статусами
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        } else {
            System.out.println("Такой задачи не существует");
        }

    }

    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
        } else {
            System.out.println("Такой подзадачи не существует");
        }
    }

    public void updateEpic(Epic epic) {

        if (epics.containsKey(epic.getId())) {
            getEpicStatus(epic.getId());
            epics.put(epic.getId(), epic);
        }
    }

    public Status getEpicStatus(int epicId) {
        Status epicStatus = Status.IN_PROGRESS;
        List<Subtask> tmpSubtask = new ArrayList<>();
        if (epics.containsKey(epicId)) {
            if (!subtasks.isEmpty()) {
                for (Subtask subtask : subtasks.values()) {
                    if (subtask.getEpicId() == epicId) {
                        tmpSubtask.add(subtask);
                    }
                }
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
        }
        return epicStatus;
    }

}
