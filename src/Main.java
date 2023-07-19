public class Main {

    public static void main(String[] args) {


        System.out.println("Поехали!");
        Task task = new Task("Работа", "Заработать 1000 рублей");
        Task task1 = new Task("Испечь пироги", "Приготовить пироги из капусты");
        TaskManager manager = new TaskManager();
        manager.addTask(task);
        manager.addTask(task1);
        System.out.println(manager.tasks.values());
        System.out.println(manager.getTask(3));
    }
}
