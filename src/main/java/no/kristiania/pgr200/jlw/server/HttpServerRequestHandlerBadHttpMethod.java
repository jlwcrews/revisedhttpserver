package no.kristiania.pgr200.jlw.server;

import java.io.IOException;

public class HttpServerRequestHandlerBadHttpMethod implements HttpServerRequestHandler{

    @Override
    public boolean HandleRequest(HttpServerRequest request, HttpServerResponse response) throws IOException {
        if(HttpServerConfig.SUPPORTED_METHODS.contains(request.getHttpMethod())){
            return false;
        } else{
            response.setStatusCode(405);
            return true;
        }
    }
}
