package no.kristiania.pgr200.jlw;

import java.io.IOException;
import java.io.OutputStream;

public interface HttpServerWriter {

    void write(OutputStream stream, HttpServerResponse response) throws IOException;

}
