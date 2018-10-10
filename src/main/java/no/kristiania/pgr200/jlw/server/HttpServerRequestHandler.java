package no.kristiania.pgr200.jlw.server;

import java.io.IOException;

public interface HttpServerRequestHandler {

    boolean HandleRequest(HttpServerRequest httpServerRequest, HttpServerResponse httpServerResponse) throws IOException;

}
