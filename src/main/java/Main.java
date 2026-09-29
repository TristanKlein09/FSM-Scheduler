import util.Util;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Main {
    //TODO: Host and port to be stored in the scheduler.properties file
    public static void main(String[] args) throws IOException {
        Socket clientSocket =  new Socket("localhost", 6173);
        System.out.println("Connected to server");

        //First we send a request to the server then get a response, so OutPutStream is first (writer), then InputStream (reader)

        //BufferedWriter sends data
        OutputStream outputStream = clientSocket.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream)); //Writes in bytes?

        //Creating the request
        Util util = new Util();
        String body = "Hello World";

        bufferedWriter.write("POST /test HTTP/1.1\r\n");
        bufferedWriter.write("Host: localhost\r\n");
        bufferedWriter.write("Content-Length: " + Util.contentLength(body.getBytes(StandardCharsets.UTF_8)) + "\r\n");
        bufferedWriter.write("Content-Type: text/plain\r\n");
        bufferedWriter.write("\r\n"); // VERY IMPORTANT: blank line before body
        bufferedWriter.write(body);
        bufferedWriter.flush();
        //InputStream recieves data from the server
        InputStream serverInputStream = clientSocket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(serverInputStream));

        //Reads until an empty line is reached - until it reaches the body
        String line;
        int length = 0; //content length
        while (!(line = bufferedReader.readLine()).isEmpty()) {
            if (line.contains("Content-Length:")) {
                length =  Integer.parseInt(line.split(":")[1].trim()); //Gets only the value of the length and stores it
            }
            System.out.println(line);
        }
        //Reading the body
        char [] bodyChars = new char[length];
        bufferedReader.read(bodyChars, 0, length);
        System.out.println(new String(bodyChars));

        clientSocket.close();

    }
}
