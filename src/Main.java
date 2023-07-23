
public class Main {

    public static void main(String[] args) {


        TaskManager manager = new TaskManager();

        Task task1 = new Task("Работа", "Заработать 1000 рублей");
        Task task2 = new Task("Испечь пироги", "Приготовить пироги из капусты");

        Task newTask2 = new Task(2, "Обучение", "Сдать ТЗ3", Status.IN_PROGRESS, TypeTask.TASK);

        Epic epic1 = new Epic("Переезд", "Перевезти все вещи в новую квартиру");
        Epic epic2 = new Epic("Подъем", "Поднять все вещи");

        Subtask subtask1 = new Subtask("Собрать вещи", "аккурасно сложить в чемодан", 3);
        Subtask subtask2 = new Subtask("Чемодан", "вынести в коридор", 3);
        Subtask subtask3 = new Subtask("Лифт", "вызвать лифт", 4);

        Subtask newSubtask3 = new Subtask(7, "Лестница", "Лифт не работает. Пойти по лестнице", Status.DONE, TypeTask.SUBTASK, 4);

        System.out.println("Добавление  всех задач, эпиков, и подзадач:");
        manager.addTask(task1);
        manager.addTask(task2);
        manager.addEpic(epic1);
        manager.addEpic(epic2);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        manager.addSubtask(subtask3);


        System.out.println("Получение списка всех задач, эпиков, и подзадач: \n ");
        for (Task value : manager.getTasks().values()) {
            System.out.println(value);
        }

        for (Epic value : manager.getEpics().values()) {
            System.out.println(value);
        }

        for (Subtask value : manager.getSubtasks().values()) {
            System.out.println(value);
        }


        System.out.println("\n");
        System.out.println("Получение по идентификатору.\n ");

        System.out.println(manager.getTask(1));
        System.out.println(manager.getEpic(3));
        System.out.println(manager.getSubtask(5));


        System.out.println("\n");
        System.out.println("Получение списка всех подзадач определённого эпика \n ");
        int epicId = 3;
        if (manager.getEpics().containsKey(epicId)) {
            if (manager.getListByEpic(epicId).size() != 0) {
                System.out.println(manager.getListByEpic(epicId));
            } else {
                System.out.println("У эпика нет подзадач");
            }
        } else {
            System.out.println("Такого эпика не существует");
        }

        System.out.println("\n");
        System.out.println("Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.\n");
        manager.updateTask(newTask2);
        System.out.println("Обновленная задача " + manager.getTask(2));
        manager.updateSubtask(newSubtask3);
        System.out.println("Обновленная подзадача " + manager.getSubtask(7));

        System.out.println("\n");
        System.out.println("Статус эпика\n");
        manager.getEpicStatus(3);
        System.out.println(manager.getEpicStatus(3));


        System.out.println("\n");
        System.out.println("Удаление по идентификатору.\n");
        manager.removeByIdTask(1);
        manager.removeByIdSubtask(7);

        System.out.println("\n");
        System.out.println("Удаление всех задач.\n");
        manager.clearAllEpic();

    }
}
