package no.kristiania.pgr200.jlw.server;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class HttpServerConnectionHandler extends Thread {

    private final List<HttpServerRequestHandler> requestHandlers;
    private final HttpServerParserRequest parser;
    private final HttpServerWriter writer;
    private Socket clientSocket;

    public HttpServerConnectionHandler(Socket clientSocket, List<HttpServerRequestHandler> requestHandlers, HttpServerParserRequest parser, HttpServerWriter writer) {
        System.out.println();

        this.clientSocket = clientSocket;
        this.requestHandlers = requestHandlers;
        this.parser = parser;
        this.writer = writer;
    }

    @Override
    public void run() {
        HttpServerRequest request = null;
        HttpServerResponse response;
        try {
            request = parser.parse(clientSocket.getInputStream());
            response = new HttpServerResponse(404, request.getHttpVersion());

            for (HttpServerRequestHandler handler: requestHandlers) {
                if (handler.HandleRequest(request, response))
                    break;
            }
        }
        catch(IOException e) {
            response = new HttpServerResponse(500, request.getHttpVersion());
        }
        try{
            writer.write(clientSocket.getOutputStream(), response);
        }catch(IOException e){
            System.err.println("Error writing response");
        }
    }
}
