package no.kristiania.pgr200.jlw;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class HttpServerConnectionHandler extends Thread {

    private final List<HttpServerRequestHandler> requestHandlers;
    private final HttpServerParser parser;
    private final HttpServerWriter writer;
    private Socket clientSocket;
    private InputStream input;
    private OutputStream output;

    public HttpServerConnectionHandler(Socket clientSocket, List<HttpServerRequestHandler> requestHandlers, HttpServerParser parser, HttpServerWriter writer) {
        System.out.println();

        this.clientSocket = clientSocket;
        try {
            input = clientSocket.getInputStream();
            output = clientSocket.getOutputStream();
        } catch (IOException e) {
            System.out.println("Error opening input and output streams.");
            e.printStackTrace();
        }
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
            StringWriter stack = new StringWriter();
            PrintWriter writer = new PrintWriter(stack);
            e.printStackTrace(writer);
            String httpVersion = request != null ? request.getHttpVersion() : "HTTP/1.1";
            response = new HttpServerResponse(500, httpVersion);
            //response.setBody(new StringResponseBody(stack.toString()));
        }
        try{
            writer.write(clientSocket.getOutputStream(), response);
        }catch(IOException e){
            System.err.println("Error writing response");
            e.printStackTrace();
        }
    }
}
