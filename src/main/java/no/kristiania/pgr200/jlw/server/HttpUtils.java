package no.kristiania.pgr200.jlw.server;

import java.io.IOException;
import java.io.InputStream;

public class HttpUtils {

    public InputStream input;

    public static String readNextLine(InputStream input) throws IOException {
        StringBuilder currentLine = new StringBuilder();
        int c;
        while ((c = input.read()) != -1) {
            if (c == '\r') {
                input.mark(1);
                c = input.read();
                if (c != '\n') {
                    input.reset();
                }
                break;
            }
            currentLine.append((char)c);
        }
        return currentLine.toString();
    }
}
