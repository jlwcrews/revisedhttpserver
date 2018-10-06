package no.kristiania.pgr200.jlw;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServerListener {
    private int actualPort;

    public HttpServerListener() {
        System.out.println("Starting server... \n");
    }

    public void start() throws IOException {
        HttpServerConfig config = new HttpServerConfig();
        ServerSocket serverSocket = new ServerSocket(0);
        this.actualPort = serverSocket.getLocalPort();
        new Thread(() ->  serverThread(serverSocket)).start();
    }

    public void serverThread(ServerSocket serverSocket) {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                Thread t = new HttpServerRequestHandler(clientSocket);
                t.start();
            } catch (IOException e) {
                System.out.println("ZOMG SERVER WENT SPLODE");
                e.printStackTrace();
            }
        }
    }

    public int getPort() {
        return actualPort;
    }
}
