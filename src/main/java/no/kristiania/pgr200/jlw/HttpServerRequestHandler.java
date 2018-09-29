package no.kristiania.pgr200.jlw;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.Socket;

public class HttpServerRequestHandler extends Thread {

    private Socket clientSocket;
    private InputStream input;
    private OutputStream output;

    public HttpServerRequestHandler(Socket clientSocket, InputStream input, OutputStream output) {
        this.clientSocket = clientSocket;
        this.input = input;
        this.output = output;
    }

    public void start(){
        //Create object to hold final response
        HttpServerResponse response = new HttpServerResponse();
        //create object to hold the request
        HttpServerRequest request = new HttpServerRequest(input);
        //create factory to create parser
        HttpServerParserFactory parserFactory = new HttpServerParserFactory(input);
        //create parser
        try {
            HttpServerParser parser = parserFactory.createParser(parserFactory.getRequestType());
            try {
                parser.parse();
            } catch (IOException e) {
                System.out.println("Error parsing request.");
                e.printStackTrace();
            }
        } catch(IllegalArgumentException iae){
            System.out.println("Error creating parser.");
            iae.printStackTrace();
        }
        //instantiate the builder factory
        HttpServerResponseBuilderFactory builderFactory = new HttpServerResponseBuilderFactory(response);
        //create the builder, which populates the response object
        try{
            HttpServerResponseBuilder builder = builderFactory.createBuilder(request.getPath());
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