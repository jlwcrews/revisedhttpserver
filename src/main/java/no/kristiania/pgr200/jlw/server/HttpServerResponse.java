package no.kristiania.pgr200.jlw.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HttpServerResponse {

    private String httpVersion, body;
    private String contentType = "text/plain";
    private HashMap<String, String> additionalHeaders;
    private int statusCode;

    public HttpServerResponse(int statusCode, String httpVersion)
    {
        this.statusCode = statusCode;
        this.httpVersion = httpVersion;
    }

    public int getStatusCode(){
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public HashMap<String,String> getAdditionalHeaders(){
        if (additionalHeaders == null)
            additionalHeaders = new HashMap<String,String>();

        return  additionalHeaders;
    }

    public void setAdditionalHeaders(HashMap<String,String> additionalHeaders){
        this.additionalHeaders = additionalHeaders;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHttpVersion() {
        return this.httpVersion;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType(){
        return contentType;
    }

}
