import enumtype.Status;
import enumtype.TaskType;
import managers.HistoryManager;
import managers.InMemoryHistoryManager;
import managers.InMemoryTaskManager;
import managers.Managers;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

public class Main {

    public static void main(String[] args) {

        InMemoryTaskManager memoryTaskManager = (InMemoryTaskManager) Managers.getDefault();

        memoryTaskManager.addTask(new Task("Работа", "Заработать 1000 рублей"));
        memoryTaskManager.addTask(new Task("Испечь пироги", "Приготовить пироги из капусты"));


        memoryTaskManager.addEpic(new Epic("Подъем", "Поднять все вещи"));
        memoryTaskManager.addEpic(new Epic("Переезд", "Перевезти все вещи в новую квартиру"));
        memoryTaskManager.addEpic(new Epic("Подъем", "Поднять все вещи"));
        memoryTaskManager.addEpic(new Epic("Переезд", "Перевезти все вещи в новую квартиру"));
        memoryTaskManager.addEpic(new Epic("Подъем", "Поднять все вещи"));
        memoryTaskManager.addEpic(new Epic("Переезд", "Перевезти все вещи в новую квартиру"));
        memoryTaskManager.addEpic(new Epic("Подъем", "Поднять все вещи"));
        memoryTaskManager.addEpic(new Epic("Переезд", "Перевезти все вещи в новую квартиру"));

        memoryTaskManager.addSubtask(new Subtask("Собрать вещи", "аккурасно сложить в чемодан", 3));
        memoryTaskManager.addSubtask(new Subtask("Чемодан", "вынести в коридор", 3));
        memoryTaskManager.addSubtask(new Subtask("Лифт", "вызвать лифт", 4));


        System.out.println("Получение списка всех задач, эпиков, и подзадач: \n ");
        for (Task value : memoryTaskManager.getTasks().values()) {
            System.out.println(value);
        }

        for (Epic value : memoryTaskManager.getEpics().values()) {
            System.out.println(value);
        }

        for (Subtask value : memoryTaskManager.getSubtasks().values()) {
            System.out.println(value);
        }


        System.out.println("\n");
        System.out.println("Получение по идентификатору.\n ");

        System.out.println(memoryTaskManager.getTask(1));
        System.out.println(memoryTaskManager.getEpic(3));
        System.out.println(memoryTaskManager.getSubtask(11));
        System.out.println(memoryTaskManager.getTask(1));
        System.out.println(memoryTaskManager.getEpic(3));
        System.out.println(memoryTaskManager.getSubtask(11));
        System.out.println(memoryTaskManager.getTask(1));
        System.out.println(memoryTaskManager.getEpic(3));
        System.out.println(memoryTaskManager.getSubtask(11));
        System.out.println(memoryTaskManager.getTask(1));
        System.out.println(memoryTaskManager.getEpic(3));

        System.out.println("\n");

        int epicId = 3;
        System.out.println("Получение списка всех подзадач определённого эпика \n " + memoryTaskManager.getListByEpic(epicId));

        System.out.println("\n");
        System.out.println("Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.\n");
        memoryTaskManager.updateTask(new Task(2, "Обучение", "Обновить ТЗ-4", Status.IN_PROGRESS,
                TaskType.TASK));
        System.out.println("Обновленная задача " + memoryTaskManager.getTask(2));
        memoryTaskManager.updateSubtask(new Subtask(13, "Лестница", "Лифт не работает. Пойти по лестнице",
                Status.DONE, TaskType.SUBTASK, 4));
        System.out.println("Обновленная подзадача " + memoryTaskManager.getSubtask(13));

        System.out.println("\n");
        System.out.println("Статус эпика\n");
        System.out.println(memoryTaskManager.getEpicStatus(4));

        System.out.println("История");
        for (Task task : InMemoryTaskManager.historyManager.getHistory()) {
            System.out.println(" History " + task);
        }
        System.out.println("Id subtask for Epic");
        System.out.println(memoryTaskManager.getEpic(4).getSubtasksIdForEpic());

        System.out.println("\n");
        System.out.println("Удаление по идентификатору.\n");
        memoryTaskManager.removeByIdTask(1);
        memoryTaskManager.removeByIdSubtask(7);
        memoryTaskManager.removeByIdEpic(3);


        System.out.println("Получение списка всех задач, эпиков, и подзадач: \n ");
        for (Task value : memoryTaskManager.getTasks().values()) {
            System.out.println(value);
        }

        for (Epic value : memoryTaskManager.getEpics().values()) {
            System.out.println(value);
        }

        for (Subtask value : memoryTaskManager.getSubtasks().values()) {
            System.out.println(value);
        }

        System.out.println("\n");
        System.out.println("Удаление всех задач.\n");
        memoryTaskManager.clearAllEpic();

    }
}
