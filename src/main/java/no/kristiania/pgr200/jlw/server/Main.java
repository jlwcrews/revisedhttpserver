package no.kristiania.pgr200.jlw.server;

import no.kristiania.pgr200.jlw.server.*;

import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        new HttpServerConfig();
        int port = 9010;
        System.out.println("Server listening on port " + port);
        HttpServerListener listener = new HttpServerListener(
            Arrays.asList(new HttpServerRequestHandlerBadHttpMethod(),
                    new HttpServerRequestHandlerEcho(),
                    new HttpServerRequestHandlerURL(),
                    new HttpServerRequestHandlerTalk()),
                new HttpServerParserRequest(),
                new HttpServerWriterResponse()
        );
        try {
            listener.start(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
