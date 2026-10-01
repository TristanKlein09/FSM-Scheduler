package models;

import util.Util;

import java.io.BufferedReader;

public class HttpRequest {
    private BufferedReader reader;
    private final String CRLF = "\r\n";

    private String request;

    //Request line
    private String requestLine;
    private String method; //GET, POST, etc.
    private String target; //URL
    private String httpVersion = "HTTP/1.1"; //HTTP/1.1, HTTP2, etc.

    //Headers
    private String host; //Host the client is requesting
    private String userAgent = "Scheduler Desktop"; //Sort of software made the request
    //TODO: Check if accept header can be 'premade'
    private String accept; //Sort of content the client can accept
    private String contentType; //Format of the request body
    private int contentLength; //Length of the request body
    private String connection; //Connection type (keep-alive, close, etc.)
    private String authorization; //Authorization header (if present) - TODO: Figure out if needed

    //Body
    private String body;

    public HttpRequest(String method, String target, String host, String accept, String contentType, String connection, String body) {
        this.method = method;
        this.target = target;
        this.host = host;
        this.accept = accept;
        this.contentType = contentType;
        this.connection = connection;
        //If body is null, BufferedWriter will throw an error
        if (body == null) {
            this.body = "";
        } else {
            this.body = body;
        }

        this.contentLength = Util.contentLength(this.body.getBytes());

        createRequestLine();
        createRequest();
    }

    private void createRequestLine() {
        this.requestLine = this.method + " " + this.target + " " + this.httpVersion + CRLF;
        System.out.println("Request: " + this.requestLine);
    }

    private void createRequest() {
        this.request = this.requestLine +
                "Host: " + this.host + CRLF +
                "User-Agent: " + this.userAgent + CRLF +
                "Accept: " + this.accept + CRLF +
                "Content-Type: " + this.contentType + CRLF +
                "Content-Length: " + this.contentLength + CRLF +
                "Connection: " + this.connection + CRLF +
                CRLF;

        System.out.println("Request: " + this.request);

        //Might need requestBytes
    }



    //Getters and Setters
    public BufferedReader getReader() {
        return reader;
    }

    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    public String getCRLF() {
        return CRLF;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getRequestLine() {
        return requestLine;
    }

    public void setRequestLine(String requestLine) {
        this.requestLine = requestLine;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


}
