package httpServers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import managers.Managers;
import managers.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.*;
import java.net.InetSocketAddress;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpTaskServer {
    public static final int PORT = 8080;
    private static final TaskManager fileBackedTasksManager = Managers.getDefault();
    private static final Gson gson = Managers.getGson();
    private static final String ID = "id";
    private static String token;
    HttpServer httpServer;

    public static void main(String[] args) throws Exception {
       HttpTaskServer httpTaskServer = new HttpTaskServer();
      httpTaskServer.start();
    }

    public HttpTaskServer() throws IOException {

        httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks/task", new TasksHandler());
        httpServer.createContext("/tasks/epic", new EpicsHandler());
        httpServer.createContext("/tasks/subtask", new SubtasksHandler());
        httpServer.createContext("/tasks/subtasks/epic", new GetEpicSubtasks());
        httpServer.createContext("/tasks/history", new GetHistory());
        httpServer.createContext("/tasks", new GetPrioritizedTasks());

    }
    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT);
        System.out.println("Открой в браузере http://localhost:" + PORT + "/");
        httpServer.start();
    }
    public void stop(){
        System.out.println("Останавлиааем сервер");
        httpServer.stop(1);
    }
    static class TasksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            switch (method) {
                case "GET":
                    Map<String, String> params = kvQueryMap(exchange.getRequestURI().getQuery());
                    if (params.containsKey(ID) && !params.get(ID).isEmpty()) {
                        try {
                            token = gson.toJson(fileBackedTasksManager.getTask(Integer.parseInt(params.get(ID))));
                        } catch (Exception e) {
                          writeResponse(exchange, e.getMessage(), 404);
                        }
                    } else {
                        token = gson.toJson(fileBackedTasksManager.getTasks());
                    }

                   writeResponse(exchange, token, 200);
                    break;
                case "POST":
                    String body = readText(exchange);
                    Task taskFromGson = gson.fromJson(body, Task.class);
                    try {
                        if (fileBackedTasksManager.getTask(taskFromGson.getId()) != null) {
                            exchange.sendResponseHeaders(404, 0);
                            exchange.close();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Task task = new Task(taskFromGson.getName(), taskFromGson.getDescription());
                    try {
                        fileBackedTasksManager.addTask(task);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    exchange.sendResponseHeaders(200, 0);
                    exchange.close();
                    break;
                case "DELETE":
                    Map<String, String> paramsDELETE = kvQueryMap(exchange.getRequestURI().getQuery());
                    if (paramsDELETE.containsKey(ID) && !paramsDELETE.get(ID).isEmpty()) {
                        fileBackedTasksManager.removeByIdTask(Integer.parseInt(paramsDELETE.get(ID)));
                    } else {
                        fileBackedTasksManager.clearAllTask();
                    }

                    writeResponse(exchange, token, 200);
                    break;
            }


        }
    }

    static class EpicsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();

            switch (method) {
                case "GET":
                    Map<String, String> params = kvQueryMap(exchange.getRequestURI().getQuery());
                    if (params.containsKey(ID) && !params.get(ID).isEmpty()) {
                        try {
                            token = gson.toJson(fileBackedTasksManager.getEpic(Integer.parseInt(params.get(ID))));
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        token = gson.toJson(fileBackedTasksManager.getEpics());
                    }

                    writeResponse(exchange, token, 200);
                    break;
                case "POST":
                    String body = readText(exchange);
                    Task taskFromGson = gson.fromJson(body, Epic.class);
                    try {
                        if (fileBackedTasksManager.getEpic(taskFromGson.getId()) != null) {
                            exchange.sendResponseHeaders(404, 0);
                            exchange.close();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Epic epic = new Epic(taskFromGson.getName(), taskFromGson.getDescription());
                    try {
                        fileBackedTasksManager.addEpic(epic);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    exchange.sendResponseHeaders(200, 0);
                    exchange.close();
                    break;
                case "DELETE":
                    Map<String, String> paramsDELETE = kvQueryMap(exchange.getRequestURI().getQuery());
                    if (paramsDELETE.containsKey(ID) && !paramsDELETE.get(ID).isEmpty()) {
                        fileBackedTasksManager.removeByIdEpic(Integer.parseInt(paramsDELETE.get(ID)));
                    } else {
                        fileBackedTasksManager.clearAllEpic();
                    }

                    exchange.sendResponseHeaders(200, 0);
                    exchange.close();
                    break;
            }

        }
    }

    static class SubtasksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            String[] urlToken = exchange.getRequestURI().toString().split("/");

            switch (method) {
                case "GET":
                    Map<String, String> params = kvQueryMap(exchange.getRequestURI().getQuery());
                    if (params.containsKey(ID) && !params.get(ID).isEmpty()) {
                        try {
                            token = gson.toJson(fileBackedTasksManager.getSubtask(Integer.parseInt(params.get(ID))));
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        token = gson.toJson(fileBackedTasksManager.getSubtasks());
                    }

                   writeResponse(exchange, token, 200);
                    break;
                case "POST":
                    String body = readText(exchange);
                    Subtask taskFromGson = gson.fromJson(body, Subtask.class);
                    try {
                        if (fileBackedTasksManager.getSubtask(taskFromGson.getId()) != null) {
                            exchange.sendResponseHeaders(404, 0);
                            exchange.close();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Subtask subtask = new Subtask(taskFromGson.getName(), taskFromGson.getDescription(), taskFromGson.getEpicId());
                    try {
                        fileBackedTasksManager.addSubtask(subtask);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    exchange.sendResponseHeaders(200, 0);
                    exchange.close();
                    break;
                case "DELETE":
                    Map<String, String> paramsDELETE = kvQueryMap(exchange.getRequestURI().getQuery());
                    if (paramsDELETE.containsKey(ID) && !paramsDELETE.get(ID).isEmpty()) {
                        fileBackedTasksManager.removeByIdSubtask(Integer.parseInt(paramsDELETE.get(ID)));
                    } else {
                        fileBackedTasksManager.clearAllSubtask();
                    }

                    exchange.sendResponseHeaders(200, 0);
                    exchange.close();
                    break;
            }
        }
    }

    static class GetEpicSubtasks implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            Map<String, String> params = kvQueryMap(exchange.getRequestURI().getQuery());
            if (params.containsKey(ID) && !params.get(ID).isEmpty()) {
                token = gson.toJson(fileBackedTasksManager.getListByEpic(Integer.parseInt(params.get(ID))));
                writeResponse(exchange, token, 200);
            } else{
                writeResponse(exchange, "Некорректный запрос", 404);
            }

        }
    }

    static class GetHistory implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            writeResponse(exchange,gson.toJson(Managers.getDefaultHistory()), 0 );
        }
    }

    static class GetPrioritizedTasks implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            writeResponse(exchange, gson.toJson(fileBackedTasksManager.getPrioritizedTasks()), 200);
        }
    }

    private static Map<String, String> kvQueryMap(String query) {
        Map<String, String> result = new HashMap<>();
        if (query == null) {
            return result;
        }
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            } else {
                result.put(entry[0], "");
            }
        }
        return result;
    }

    public static String readText(HttpExchange exchange) throws IOException {
        InputStreamReader streamReader = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        return bufferedReader.readLine();
    }
    private static void writeResponse(HttpExchange exchange, String response, int code) throws IOException {
        if (response.isBlank()) {
            exchange.sendResponseHeaders(code, 0);
        } else {
            byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(code, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
        exchange.close();
    }

}



