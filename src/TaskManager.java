import java.util.HashMap;

public class TaskManager {
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    int elementId = 1;

    //Получение списка всех задач
    public Task getTask(int id) {
        Task task = null;
        if (tasks.containsKey(id)) {
            task = tasks.get(id);
        } else {
            System.out.println("Такой задачи нет");
        }
        return task;
    }
    public Epic getEpic(int id) {
        Epic epic = null;
        if (epics.containsKey(id)) {
            epic = epics.get(id);
        } else {
            System.out.println("Такого эпика нет");
        }
        return epic;
    }
    public Subtask getSubtask(int id) {
        Subtask subtask = null;
        if (subtasks.containsKey(id)) {
            subtask = subtasks.get(id);
        } else {
            System.out.println("Такой подзадачи нет");
        }
        return subtask;
    }

    public void clearAllTask() {
        // Удаление всех задач.
    }

    public void addTask(Task task) {
        //Создание. Сам объект должен передаваться в качестве параметра.
        if(task.getId() == 0){
            task.setId(elementId);
            elementId++;
        }
        tasks.put(task.getId(), task);
    }

    public void updateTask(int id) {
        //Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    }

    public void removeByID(int id) {
        //Удаление по идентификатору.
    }

    public void gettingListByEpic() {
        //Получение списка всех подзадач определённого эпика.
    }


}
