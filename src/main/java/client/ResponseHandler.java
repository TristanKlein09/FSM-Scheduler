package client;

import models.HttpResponse;

import java.io.BufferedReader;

public class ResponseHandler {
    private BufferedReader reader;

    public ResponseHandler(BufferedReader reader) {
        this.reader = reader;

        //After fix
        HttpResponse response = createResponseObj();
    }

    public HttpResponse createResponseObj() {
        return new HttpResponse(reader);
    }
}
