package managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import httpServers.KVTaskClient;
import httpServers.LocalDateTimeAdapter;

import java.time.LocalDateTime;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();

    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefault(String url) throws InterruptedException {
        return new HttpTaskManager(url, new KVTaskClient(url));
    }
    public static Gson getGson(){
        GsonBuilder gsonBuilder = new Gson()
                .newBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting()
                .create()
                .newBuilder();
        return gsonBuilder.create();
    }
}
