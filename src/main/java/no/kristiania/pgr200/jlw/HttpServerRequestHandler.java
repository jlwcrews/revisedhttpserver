package no.kristiania.pgr200.jlw;

import java.io.IOException;

public interface HttpServerRequestHandler {

    boolean HandleRequest(HttpServerRequest httpServerRequest, HttpServerResponse httpServerResponse) throws IOException;

}
