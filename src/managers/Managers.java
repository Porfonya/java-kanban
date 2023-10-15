package managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import servers.HttpTaskManager;
import servers.KVTaskClient;

import java.time.LocalDateTime;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();

    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
    public static TaskManager getDefault(String url) throws InterruptedException {
        return new HttpTaskManager(url);
    }


}
