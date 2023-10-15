package tasksTests;

import exceprions.ManagerSaveException;
import managers.FileBackedTasksManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    static File file = new File("./resources", "FileBackedTasksTest.csv");
    private static final FileBackedTasksManager fileBackedTasksManager;

    static {

        fileBackedTasksManager = FileBackedTasksManager.loadFromFile(file);

    }

    public FileBackedTasksManagerTest() {
        super(fileBackedTasksManager);
    }

    @Test
    public void shouldBeEmptyTasks() throws ManagerSaveException {
        fileBackedTasksManager.clearAllTask();
        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);
        assertTrue(fileBackedTasksManager1.getTasks().isEmpty());
    }

    @Test
    public void shouldBeEpicWithoutSubtask() throws ManagerSaveException, InterruptedException {
        fileBackedTasksManager.clearAllEpic();
        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);

        Assertions.assertEquals(0, fileBackedTasksManager1.getEpics().size(), "Список эпиков не пуст");
        fileBackedTasksManager1.addEpic(epic);
        Assertions.assertEquals(1, fileBackedTasksManager1.getEpics().size(), "Список эпиков пуст");
        assertEquals(0, fileBackedTasksManager1.getListByEpic(epic.getId()).size());
    }
    @Test
    public void shouldBeEmptyHistory() throws ManagerSaveException {
        fileBackedTasksManager.getHistory().clear();
        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);
        assertTrue( fileBackedTasksManager1.getHistory().isEmpty(), "История задач не пуста");
    }

}