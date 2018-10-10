package no.kristiania.pgr200.jlw;

import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        new HttpServerConfig();
        int port = 9000;
        HttpServerListener listener = new HttpServerListener(
            Arrays.asList(new HttpServerRequestHandlerEcho(),
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
