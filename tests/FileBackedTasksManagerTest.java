
import exceprions.ManagerSaveException;
import managers.FileBackedTasksManager;
import org.junit.jupiter.api.Test;


import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    static File file = new File("./resources" , "FileBackedTasksTest.csv");
    private static final FileBackedTasksManager fileBackedTasksManager;

    static {

            fileBackedTasksManager = FileBackedTasksManager.loadFromFile(file);

    }

    public FileBackedTasksManagerTest() {
        super(fileBackedTasksManager);
    }

    @Test
    void shouldBeEmptyTasks() throws ManagerSaveException {

        assertTrue(fileBackedTasksManager.getTasks().isEmpty());
    }

    @Test
    void shouldBeEpicWithoutSubtask() throws ManagerSaveException {

    }






}