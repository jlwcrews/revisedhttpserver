package no.kristiania.pgr200.jlw.server;

import java.io.IOException;

public class HttpServerRequestHandlerTalk implements HttpServerRequestHandler {

    @Override
    public boolean HandleRequest(HttpServerRequest httpServerRequest, HttpServerResponse httpServerResponse) throws IOException {
        return false;
    }
}
