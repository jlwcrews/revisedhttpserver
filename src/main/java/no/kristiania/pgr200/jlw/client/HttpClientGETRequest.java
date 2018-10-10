package no.kristiania.pgr200.jlw.client;

import java.io.IOException;
import java.net.Socket;

public class HttpClientGETRequest {

    private String hostname;
    private String path;
    private String method = "GET";
    private int port;

    public HttpClientGETRequest(String hostname, int port, String path) {
        this.hostname = hostname;
        this.path = path;
        this.port = port;
    }

    public HttpClientGETRequest(String hostname, int port, String path, String method) {
        this.hostname = hostname;
        this.path = path;
        this.port = port;
        this.method = method;
    }

    public HttpClientResponse execute() throws IOException {
        try(Socket socket = new Socket(hostname, port)) {
            socket.getOutputStream()
                    .write((method + " " + path + " HTTP/1.1\r\n").getBytes());
            socket.getOutputStream()
                    .write(("Host: " + hostname + "\r\n").getBytes());
            socket.getOutputStream()
                    .write("Connection: close\r\n".getBytes());
            socket.getOutputStream().write("\r\n".getBytes());


            return new HttpClientResponse(socket);
        }
    }

}
