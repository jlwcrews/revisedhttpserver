package no.kristiania.pgr200.jlw.client;

import java.io.IOException;
import java.net.Socket;

public class HttpClientPOSTRequest {

    private String hostname;
    private String path;
    private int port;
    private String body;

    public HttpClientPOSTRequest(String hostname, int port, String path, String body) {
        this.hostname = hostname;
        this.path = path;
        this.port = port;
        this.body = body;

    }

    public HttpClientResponse execute() throws IOException {
        try(Socket socket = new Socket(hostname, port)) {
            socket.getOutputStream()
                    .write(("POST " + path + " HTTP/1.1\r\n").getBytes());
            socket.getOutputStream()
                    .write(("Host: " + hostname + "\r\n").getBytes());
            socket.getOutputStream()
                    .write("Connection: close\r\n".getBytes());
            socket.getOutputStream()
                    .write("Content-Type: application/x-www-form-urlencoded\r\n".getBytes());
            socket.getOutputStream()
                    .write(("Content-Length: " + body.length() + "\r\n").getBytes());
            socket.getOutputStream().write("\r\n".getBytes());
            socket.getOutputStream()
                    .write((body + "\r\n").getBytes());



            return new HttpClientResponse(socket);
        }
    }

}
