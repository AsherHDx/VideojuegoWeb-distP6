package com.example.pract6_ServWeb;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Client {
    private static HttpRequest request;
    private static HttpResponse<String> response;
    private static String currentUser;

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Puedes comenzar a utilizar comandos:");
        while (true){
            System.out.print("Comando: ");
            String command = scanner.nextLine();
            String[] parts = command.split(" ");
            switch (parts[0]){
                case "CHANGE_USER":
                    System.out.print("Ingrese el nombre de usuario: ");
                    currentUser = scanner.nextLine();
                    System.out.println("Ahora estás controlando a: " + currentUser);
                    request = null;
                    break;
                case "PRUEBA":
                    request = HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:8080/game/saludo"))
                            .GET()
                            .build();
                    break;
                case "REGISTER":
                    System.out.print("Ingrese el nombre de usuario del nuevo jugador:");
                    String username = scanner.nextLine();
                    request = HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:8080/game/addPlayer/"+username))
                            .POST(HttpRequest.BodyPublishers.noBody())
                            .build();
                    break;
                case "MOVE":
                    if(!(parts.length==3)){
                        System.out.print("Comando MOVE inválido (Formato: MOVE X Y)");
                    }
                    int posX = Integer.parseInt(parts[1]);
                    int posY = Integer.parseInt(parts[2]);
                    String jsonBody = String.format("{\"posX\":%d, \"posY\":%d}",posX,posY);
                    request = HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:8080/game/move/"+currentUser))
                            .header("Content-Type","application/json")
                            .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                            .build();
                    break;
                case "ATTACK":
                    request = HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:8080/game/attack/"+currentUser))
                            .PUT(HttpRequest.BodyPublishers.noBody())
                            .build();
                    break;
                case "STATUS":
                    request = HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:8080/game/status/"+currentUser))
                            .GET()
                            .build();
                    break;
                case "DELETE_USER":
                    request = HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:8080/game/delete/"+currentUser))
                            .PUT(HttpRequest.BodyPublishers.noBody())
                            .build();
                    break;
                default:
                    System.out.print("Comando no reconocido");
                    request = null;
                    break;
            }
            if(request!=null){
                response = httpClient.send(request,HttpResponse.BodyHandlers.ofString());
                System.out.println("Respuesta del servidor: " + response.body());
            }
        }
    }
}
