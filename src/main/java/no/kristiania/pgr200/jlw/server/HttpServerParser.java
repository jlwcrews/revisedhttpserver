package no.kristiania.pgr200.jlw.server;

import java.io.IOException;
import java.io.InputStream;

public interface HttpServerParser {

    HttpServerRequest parse(InputStream input) throws IOException;

}
