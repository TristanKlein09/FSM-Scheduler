package client;

import com.google.gson.Gson;
import gui.controllers.LoginController;
import models.HttpRequest;
import models.HttpResponse;
import models.HttpStatus;
import models.Pair;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class HttpClient {
    private static HttpClient instance;
    Socket clientSocket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    //TODO: Host and port to be stored in the scheduler.properties file
    //TODO: Figure out what to put for host
    //TODO: Figure out how to keep the connection alive on the client side
    public void startClient() {
        try {
            this.clientSocket =  new Socket("localhost", 9999);
            this.bufferedReader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
            System.out.println("Connected to server");

        } catch (Exception e) {
            System.out.println("Error in startClient() method");
            e.printStackTrace();
        }
    }

    /**
     * Responsible for parsing a {@link HttpRequest} object into a format that can be sent to the server.
     * @param httpRequest The {@link HttpRequest} object to be sent to the server.
     */
    public void sendHttpRequestObj(HttpRequest httpRequest) {
        try {
            System.out.println(httpRequest.getRequestLine());

            //Sending the request
            this.bufferedWriter.write(httpRequest.getRequest());
            this.bufferedWriter.write(httpRequest.getBody());
            this.bufferedWriter.flush();

        } catch (Exception e) {
            System.out.println("Error in sendHttpRequestObj() method: " + e.getMessage());
        }
    }

    /**
     * Responsible for reading the data send by the server and parsing it into a HttpResponse. It blocks
     * the program/thread until a response is received.
     * @return {@link HttpResponse}
     */
    public HttpResponse createResponse() {
        return new ResponseHandler(this.bufferedReader).createResponseObj();
    }

    /**
     * Responsible for handling the whole process of user login. Creates a response to be sent to the server and receives a response.
     * Updates the UI accordingly
     * @param username
     * @param password
     * @return {@link Pair}{@code <Boolean, String>} where the value is a boolean for if the login was successful, the key is a String to change the
     * prompt label to if the value is unsuccessful
     */
    public Pair<Boolean, String> login(String username, String password) throws ExecutionException, InterruptedException {
        //Use completable future as we are returning a value
        CompletableFuture<Pair<Boolean, String>> future = CompletableFuture.supplyAsync(() -> {
            Pair<Boolean, String> loginValid = null;
            try {
                createLoginRequest(username, password);
                HttpResponse response = createResponse();

                switch (response.getStatus()) {
                    case ACCEPTED:
                        loginValid = new Pair<Boolean, String>(true, "");
                        break;
                    case UNAUTHORIZED:
                        loginValid =  new Pair<Boolean, String>(false, "Wrong username or password");
                        break;
                    default: //Assume some error has occurred
                        loginValid =  new Pair<Boolean, String>(false, "An error has occurred, try again later");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error in login method");
                e.printStackTrace();
            }

            return loginValid;
        });

        return future.get();
    }

    /**
     *A method responsible solely for creating a HttpRequest object specific for user login
     * and then sending the request to the server.
     * @param username
     * @param password
     */
    public void createLoginRequest(String username, String password) {
        try {
            String body = new Gson().toJson(Map.of(
                    "username", username,
                    "password", password
            ));

            HttpRequest request = new HttpRequest("POST", "/login", "", "text/plain", "application/json", "keep-alive", body);
            sendHttpRequestObj(request);

        } catch (Exception e) {
            System.out.println("Error in createLoginRequest() method: " + e.getMessage());
        }
    }

    //----- Getters and setters -----
    public static HttpClient getInstance() {
        if (instance == null) {
            instance = new HttpClient();
        }
        return instance;
    }


}
