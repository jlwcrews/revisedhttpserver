package no.kristiania.pgr200.jlw;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpServerRequestHandler extends Thread {

    private Socket clientSocket;
    private InputStream input;
    private OutputStream output;

    public HttpServerRequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            input = clientSocket.getInputStream();
            output = clientSocket.getOutputStream();
        } catch (IOException e) {
            System.out.println("Error opening input and output streams.");
            e.printStackTrace();
        }
    }

    public void start(){
        //Create object to hold final response
        HttpServerResponse response = new HttpServerResponse();
        //create object to hold the request
        HttpServerRequest request;
        //create parser
        HttpServerParser parser = new HttpServerParser();
        //return a parsed request
        try {
            request = parser.parse(input);
        } catch (IOException e) {
            request = new HttpServerRequest(400);
            System.out.println("Error parsing the input stream.");
        }
        //instantiate the builder factory
        HttpServerResponseBuilderFactory builderFactory = new HttpServerResponseBuilderFactory(request, response);
        //create the builder, which populates the response object
        try{
            HttpServerResponseBuilder builder = builderFactory.createBuilder();
            builder.createResponse();
        } catch(IOException ioe){
            System.out.println("Error creating builder.");
            ioe.printStackTrace();
        }


        //instantiate writer object
        HttpServerWriter writer = new HttpServerWriter(output, response);
        //write the response to the socket
        try {
            writer.write();
        } catch(IOException ioe){
            System.out.println("Error writing response to socket.");
            ioe.printStackTrace();
        }

        try {
            output.flush();
        } catch (IOException e) {
            System.out.println("Error flushing output stream.");
            e.printStackTrace();
        }
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Error closing socket.");
            e.printStackTrace();
        }

    }
}
