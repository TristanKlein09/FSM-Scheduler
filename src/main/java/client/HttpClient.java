package client;

import models.HttpRequest;
import models.HttpResponse;
import util.Util;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class HttpClient {
    Socket clientSocket;

    //TODO: Host and port to be stored in the scheduler.properties file
    public void startClient() {
        try {
            clientSocket =  new Socket("localhost", 6173);
            System.out.println("Connected to server");

            //First we send a request to the server then get a response,
            //so OutPutStream is first (writer), then InputStream (reader)

            //BufferedWriter sends data
            OutputStream outputStream = clientSocket.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

            //Sending the request
            //TODO: Figure out what to put for host
            HttpRequest request = new HttpRequest("GET", "/", "", "text/plain", "text/plain", "close", "Test");
            sendHttpRequestObj(request);

            //InputStream recieves data from the server
            InputStream serverInputStream = clientSocket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(serverInputStream));

            //Response handler stuff here instead of reading the things manually
            ResponseHandler responseHandler = new ResponseHandler(bufferedReader);
            HttpResponse response = responseHandler.createResponseObj();


            //Reads until an empty line is reached - until it reaches the body
//            String line;
//            int length = 0; //content length
//            while (!(line = bufferedReader.readLine()).isEmpty()) {
//                if (line.contains("Content-Length:")) {
//                    length =  Integer.parseInt(line.split(":")[1].trim()); //Gets only the value of the length and stores it
//                }
//                System.out.println(line);
//            }
//
//            //Reading the body
//            char [] bodyChars = new char[length];
//            bufferedReader.read(bodyChars, 0, length);
//            System.out.println(new String(bodyChars));

            clientSocket.close();
        }
        catch (IOException e) {
            System.out.println("Error in startClient() method: " + e.getMessage());
        }
    }

    private void sendHttpRequestObj(HttpRequest httpRequest) throws IOException {
        try {
            //BufferedWriter sends data
            OutputStream outputStream = this.clientSocket.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

            //Sending the request
            bufferedWriter.write(httpRequest.getRequest());
            bufferedWriter.write(httpRequest.getBody());
            bufferedWriter.flush();

        } catch (Exception e) {
            System.out.println("Error in sendHttpRequestObj() method: " + e.getMessage());
        }
    }

}
