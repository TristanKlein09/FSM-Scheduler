package client;

import models.HttpResponse;
import models.HttpStatus;

import java.io.BufferedReader;

public class ResponseHandler {
    private BufferedReader reader;

    public ResponseHandler(BufferedReader reader) {
        this.reader = reader;
    }

    public HttpResponse createResponseObj() {
        return new HttpResponse(reader);
    }
}
