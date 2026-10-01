package models;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpResponse {
    private String crlf = "\r\n";
    private BufferedReader reader;

    private String response;
    private byte[] responseBytes;

    //Status Line
    private String statusLine;
    private String httpVersion = "HTTP/1.1";
    private HttpStatus status;

    //Headers
    private List<String> headers = new ArrayList<>();
    private int contentLength;
    private String contentType;
    private String connection;
    //Server header not needed - only one server

    //Body
    private String body;

    public HttpResponse(BufferedReader reader) {
        this.reader = reader;
        parseResponse();
    }

    private void parseResponse() {
        parseStatusLine();
        parseHeaders();
        parseBody();
    }

    private void parseStatusLine() {
        try {
            //Only one line, no need to iterate through it
            this.statusLine = this.reader.readLine();
            System.out.println(this.statusLine);
            String[] statusLineArray = this.statusLine.split(" "); //Splits status line into an array

            this.httpVersion = statusLineArray[0];
            this.status = HttpStatus.fromCode(Integer.parseInt(statusLineArray[1])); //TODO: Double check to see if this works

            System.out.println(this.httpVersion);
            System.out.println(this.status);

        } catch (Exception e) {
            System.out.println("Error while parsing status line: " + e.getMessage());
        }
    }

    //Parses the headers and calls a method to set values to the corresponding attributes
    private void parseHeaders() {
        try {
            String line;
            //Ends until an empty line is reached - after this there will be body
            while (!(line = reader.readLine()).isEmpty()) {
                headers.add(line);
                handleHeaderLine(line);
            }
        } catch (IOException e) {
            System.out.println("Error while parsing headers: " + e.getMessage());
        }
    }

    //Take a header line and finds its corresponding header and sets the value of that header in the HttpResponse object
    private void handleHeaderLine(String line) {
        switch (line.split(":")[0].trim().toLowerCase()) {
            case "content-type":
                this.contentType = line.substring(line.indexOf(":") + 1).trim();
                break;
            case "content-length":
                this.contentLength = Integer.parseInt(line.substring(line.indexOf(":") + 1).trim());
                break;
            case "connection":
                this.connection = line.substring(line.indexOf(":") + 1).trim();
                break;
            default:
                System.out.println("Unknown header: " + line); //TODO: Log unknown headers
                break;
        }
    }

    //Fully parses the body and sets it to the body attribute
    private void parseBody() {
        try {
            char[] bodyChars = new char[this.contentLength];
            reader.read(bodyChars, 0, this.contentLength);
            this.body = new String(bodyChars);
            System.out.println("Body: " + this.body);

        } catch (IOException e) {
            System.out.println("Error while parsing body: " + e.getMessage());
        }
    }

}
