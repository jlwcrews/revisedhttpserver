package no.kristiania.pgr200.jlw.server;

import java.io.IOException;

public interface HttpServerRequestHandler {

    boolean HandleRequest(HttpServerRequest request, HttpServerResponse response) throws IOException;

}
